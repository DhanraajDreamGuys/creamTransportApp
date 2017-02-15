package co.in.dreamguys.cream.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PaysheetReport implements Serializable{

    private List<Data> data = new ArrayList<Data>();

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }


}