package co.in.dreamguys.cream.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user5 on 15-02-2017.
 */

public class RepairSheetReport {
    private List<RepairSheetData> data = new ArrayList<RepairSheetData>();

    public List<RepairSheetData> getData() {
        return data;
    }

    public void setData(List<RepairSheetData> data) {
        this.data = data;
    }

}
