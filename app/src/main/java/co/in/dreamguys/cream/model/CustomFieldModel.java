package co.in.dreamguys.cream.model;

import java.util.List;

/**
 * Created by user5 on 01-03-2017.
 */

public class CustomFieldModel {

    private static String category;
    private boolean header;
    private static List<SubCategory> subCategory = null;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public static List<SubCategory> getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(List<SubCategory> subCategory) {
        this.subCategory = subCategory;
    }

    public boolean isHeader() {
        return header;
    }

    public void setHeader(boolean header) {
        this.header = header;
    }
}
