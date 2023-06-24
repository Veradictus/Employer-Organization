package com.app.borgapplication.ui.schedule.editSchedule;

import androidx.appcompat.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import com.app.borgapplication.R;

import java.util.ArrayList;
import java.util.Arrays;

public class ScheduleDialog extends DialogFragment {
    /* The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it. */
    public interface ScheduleDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog, Bundle bundle);
        public void onDialogNegativeClick(DialogFragment dialog);
    }

    ScheduleDialogListener listener;

    public static ScheduleDialog newInstance() {
        ScheduleDialog frag = new ScheduleDialog();
        return frag;
    }

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            listener = (ScheduleDialogListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(getActivity().toString()
                    + " must implement NoticeDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String[] timeOptions = new String[] {};
        ArrayList selectedTime = new ArrayList();  // Where we track the selected items
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        ArrayList<Integer> empInfo = getArguments().getIntegerArrayList("employee_info");
        String availability = getArguments().getString("availability");

        if(availability.contains("Both")) {
            timeOptions = new String[] {"Open", "Close"};
        }
        if(availability.contains("Day")) {
            timeOptions = new String[] {"Open"};
        }
        if(availability.contains("Evening")) {
            timeOptions = new String[] {"Close"};
        }

        // Set the dialog title
        String[] finalTimeOptions = timeOptions;
        AlertDialog.Builder confirm_schedule = builder.setTitle("CONFIRM SCHEDULE\nShifts: ")
                // Specify the list array, the items to be selected by default (null for none),
                // and the listener through which to receive callbacks when items are selected
                .setMultiChoiceItems(timeOptions, null, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which,
                                        boolean isChecked) {
                        if (isChecked) {
                            // If the user checked the item, add it to the selected items
                            if (which == 0) {
                                selectedTime.add(finalTimeOptions[which]);
                            }
                            if (which == 1) {
                                selectedTime.add(finalTimeOptions[which]);
                            }
                        } else if (selectedTime.contains("Open") || selectedTime.contains("Close")) {
                            // Else, if the item is already in the array, remove it
                            selectedTime.remove(which);
                        }
                    }
                });
                // Set the action buttons

        confirm_schedule.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
            confirm_schedule.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    // User clicked OK, so save the selectedItems results somewhere
                    // or return them to the component that opened the dialog
//                        listener.onDialogPositiveClick(ScheduleDialog.this);
                    Bundle bundle = new Bundle();
                    bundle.putStringArrayList("toSchedule",selectedTime);
                    bundle.putIntegerArrayList("employee_info", empInfo);
                    listener.onDialogPositiveClick(ScheduleDialog.this, bundle);

                }


                });

        return builder.create();
    }



}
