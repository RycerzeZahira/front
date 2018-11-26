package lodzka.politechnika.qrcode.fragment;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import lodzka.politechnika.qrcode.R;
import lodzka.politechnika.qrcode.Utils;
import lodzka.politechnika.qrcode.adapter.QRCodeAdapter;
import lodzka.politechnika.qrcode.api.ApiUtils;
import lodzka.politechnika.qrcode.api.FormApi;
import lodzka.politechnika.qrcode.api.GroupApi;
import lodzka.politechnika.qrcode.model.Elements;
import lodzka.politechnika.qrcode.model.Form;
import lodzka.politechnika.qrcode.model.Group;
import lodzka.politechnika.qrcode.model.QRCodeForm;
import lodzka.politechnika.qrcode.model.Root;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Bartek on 2018-10-28.
 */

public class CreateListFragment extends Fragment {

    private QRCodeAdapter qrCodeAdapter;
    private List<QRCodeForm> qrCodeFormList;
    private RecyclerView recyclerView;
    private Button addField;
    private TextView fieldName;
    private Button saveButton;
    private ArrayList<Elements> elementsArrayList;
    private EditText formName;
    private FormApi formApi;
    private GroupApi groupApi;
    private Spinner spinner;
    private TextView datePick;
    private ArrayList<Group> groupArrayList;
    private Spinner groupSpinner;
    private Group groupItem;
    private Calendar calendar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.qr_code_create, viewGroup, false);
        addField = view.findViewById(R.id.add_field);
        fieldName = view.findViewById(R.id.input_name);
        qrCodeFormList = new ArrayList<>();
        getState();
        qrCodeAdapter = new QRCodeAdapter(qrCodeFormList);
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(qrCodeAdapter);
        saveButton = view.findViewById(R.id.save_button);
        formName = view.findViewById(R.id.insert_list_name_edit_text);
        spinner = view.findViewById(R.id.spinner);
        groupSpinner = view.findViewById(R.id.group_spinner);
        datePick = view.findViewById(R.id.pick_date);
        elementsArrayList = new ArrayList<>();
        formApi = ApiUtils.getFormApi();
        groupApi = ApiUtils.getGroupApi();

        groupArrayList = new ArrayList<>();

        initSpinner(view);
        setGroups(view);

        calendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, dayOfMonth);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDateTextView(view);
            }
        };

        datePick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(view.getContext(), date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        groupSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                groupItem = (Group) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        addField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = fieldName.getText().toString().toLowerCase().trim();
                if (textFieldIsUnique(text)) {
                    qrCodeFormList.add(new QRCodeForm(text, spinner.getSelectedItem().toString()));
                    qrCodeAdapter.notifyDataSetChanged();
                    fieldName.setText("");
                    Utils.saveState(getContext(), qrCodeFormList);
                }
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            String rootName = Utils.randomUUID(32, -1, '_');

            @Override
            public void onClick(View v) {
                if (qrCodeFormList.size() <= 0) {
                    noData();
                    return;
                }


                if (datePick.getText().toString().matches("Pick Date")) {
                    noExpiredDate();
                    return;
                }

                if (TextUtils.isEmpty(formName.getText().toString())) {
                    formName.setError(getActivity().getResources().getString(R.string.title_cannot_be_empty));
                    return;
                }

                for (QRCodeForm qrCodeForm : qrCodeFormList) {
                    Elements elements = new Elements();
                    String type;
                    if (qrCodeForm.getFieldType().toString().matches(view.getResources().getString(R.string.text_value))) {
                        type = "STRING";
                    } else {
                        type = "DOUBLE";
                    }
                    elements.setType(type);
                    elements.setCode(Utils.randomUUID(32, -1, '_'));
                    elements.setName(qrCodeForm.getFieldName());
                    elements.setParent(rootName);
                    elementsArrayList.add(elements);
                }
                Root root = new Root();
                root.setType("GROUP");
                root.setCode(rootName);
                root.setName(formName.getText().toString());
                root.setElements(elementsArrayList);
                root.setParent(null);

                Form form = new Form();
                form.setGroupCode(groupItem.getCode());
                form.setExpiredDate(datePick.getText().toString());
                form.setRoot(root);

                formApi.createForm(form).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            success();
                        } else {
                            System.out.println(response.toString());
                            failed();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });


            }
        });

        return view;
    }


    void updateDateTextView(View view) {
        String dateFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
        datePick.setText(sdf.format(calendar.getTime()));
    }

    void initSpinner(View view) {
        List<String> items = new ArrayList<>();
        items.add(view.getResources().getString(R.string.text_value));
        items.add(view.getResources().getString(R.string.number_value));
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, items);
        spinner.setAdapter(arrayAdapter);
    }

    public void success() {
        Toast.makeText(getActivity().getBaseContext(), getActivity().getResources().getString(R.string.form_created), Toast.LENGTH_SHORT).show();
        clearState();
        Fragment fragment = new MyListsFragment();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.miscFragment, fragment).addToBackStack(null).commit();
    }

    public void failed() {
        Toast.makeText(getActivity().getBaseContext(), getActivity().getResources().getString(R.string.form_creation_failed), Toast.LENGTH_SHORT).show();
    }

    public void noData() {
        Toast.makeText(getActivity().getBaseContext(), getActivity().getResources().getString(R.string.no_fields_added), Toast.LENGTH_SHORT).show();
    }

    public void noExpiredDate() {
        Toast.makeText(getActivity().getBaseContext(), getActivity().getResources().getString(R.string.pick_expired_date), Toast.LENGTH_SHORT).show();
    }


    private void setGroups(final View view) {
        groupApi.getMyGroupsList().enqueue(new Callback<ArrayList<Group>>() {
            @Override
            public void onResponse(Call<ArrayList<Group>> call, Response<ArrayList<Group>> response) {
                groupArrayList.addAll(response.body());
                ArrayAdapter<Group> arrayAdapter = new ArrayAdapter<Group>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, groupArrayList);
                groupSpinner.setAdapter(arrayAdapter);
            }

            @Override
            public void onFailure(Call<ArrayList<Group>> call, Throwable t) {

            }
        });
    }


    private void getState() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        if (preferences.contains("list")) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<QRCodeForm>>() {
            }.getType();
            String json = preferences.getString(Utils.LIST, "");
            qrCodeFormList = gson.fromJson(json, type);
        }
    }

    private void clearState() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        if (preferences.contains(Utils.LIST)) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.remove(Utils.LIST);
            editor.apply();
        }
    }

    private boolean textFieldIsEmpty(String text) {
        return text.matches("");
    }

    private boolean textFieldIsUnique(String text) {
        if (textFieldIsEmpty(text)) {
            return false;
        }
        for (QRCodeForm qrCodeForm : qrCodeFormList) {
            if (qrCodeForm.getFieldName().matches(text)) {
                return false;
            }
        }
        return true;
    }


}
