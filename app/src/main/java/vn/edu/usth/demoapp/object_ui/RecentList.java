package vn.edu.usth.demoapp.object_ui;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

public class RecentList {
    private static MutableLiveData<List<Food>> recentlyClickedItems = new MutableLiveData<>(new ArrayList<>());

    public static MutableLiveData<List<Food>> getRecentlyClickedItemsLiveData() {
        return recentlyClickedItems;
    }

    public static void add(Food food) {
        List<Food> currentList = recentlyClickedItems.getValue();
        if (currentList == null) {
            currentList = new ArrayList<>();
        }
        currentList.add(0, food);
        recentlyClickedItems.setValue(currentList);
    }

    public static void remove(Food food) {
        List<Food> currentList = recentlyClickedItems.getValue();
        if (currentList == null) {
            currentList = new ArrayList<>();
        }
        currentList.remove(food);
        recentlyClickedItems.setValue(currentList);
    }

    public static void clear() {
        List<Food> currentList = recentlyClickedItems.getValue();
        if (currentList == null) {
            currentList = new ArrayList<>();
        }
        currentList.clear();
        recentlyClickedItems.setValue(currentList);
    }

    //move to top
    public static void moveToTop(Food food) {
        List<Food> currentList = recentlyClickedItems.getValue();
        if (currentList == null) {
            currentList = new ArrayList<>();
        }
        currentList.remove(food);
        currentList.add(0, food);
        recentlyClickedItems.setValue(currentList);
    }
}
