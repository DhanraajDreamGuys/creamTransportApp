package co.in.dreamguys.cream.model;

import java.util.List;

/**
 * Created by user5 on 08-03-2017.
 */

public class TyreRepairData {
    private String category;
    private List<TyrerepairModel> subCategory = null;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<TyrerepairModel> getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(List<TyrerepairModel> subCategory) {
        this.subCategory = subCategory;
    }
}
