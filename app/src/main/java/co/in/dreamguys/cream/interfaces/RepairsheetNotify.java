package co.in.dreamguys.cream.interfaces;

/**
 * Created by user5 on 17-02-2017.
 */

public interface RepairsheetNotify {
    void deleteRepairSheet(String id, int position);

    void viewRepairSheet(String id, int viewType);

    void updateRepairSheet(String id, String comments);


}
