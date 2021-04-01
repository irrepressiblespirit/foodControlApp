package com.skibnev.foodcontrolapp.parser;

import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.skibnev.foodcontrolapp.model.Product;
import com.skibnev.foodcontrolapp.util.App;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class not used in code because his called once when put data to database (firebase realtime database is empty)
 */
public class HtmlParser extends AsyncTask<Void, Void, Void> {

    private String url = "https://www.calc.ru/Kaloriynost-Produktov-Tablitsa-Kaloriynosti-Produktov.html";
    private int index = 0;

    // call this method when init database with products
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void startParse() {
        try {
            Document document = Jsoup.connect(url).get();
            Elements elements = document.body().select("table");
            Log.i("Table count", String.valueOf(elements.size()));
            elements.forEach(element -> element.select("tbody").forEach(elem -> elem.select("tr").forEach(this::exstractObject)));
            Log.i("Exit", "Exit from parser");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void exstractObject(Element element) {
        int size = element.select("td").size();
        Log.i("exstractObject -> ", "Count of td: " + size);
        List<String> list = new ArrayList<>();
        element.select("td").forEach(elem -> {
            if (checkElement(elem.select("p").first())) {
                Log.i("exstractObject -> ", "Elem in strong tag: " + elem.select("p").first().text());
                list.add(elem.select("p").first().text());
            }
        });
        if (list.size() > 0) {
            if (list.size() == 6) {
                Product product = new Product();
                product.setName(list.get(0));
                product.setWeight(Double.parseDouble(list.get(1).replace(",", ".")));
                product.setProtein(Double.parseDouble(list.get(2).replace(",", ".")));
                product.setFats(Double.parseDouble(list.get(3).replace(",", ".")));
                product.setCarbohydrates(Double.parseDouble(list.get(4).replace(",", ".")));
                product.setCalories(Double.parseDouble(list.get(5).replace(",", ".")));
                App.getComponent().getProductRepository().writeToDB(String.valueOf(++index), product);
            } else {
                Product product = new Product();
                product.setName(list.get(0));
                product.setWeight(0);
                product.setProtein(Double.parseDouble(list.get(1).replace(",", ".")));
                product.setFats(Double.parseDouble(list.get(2).replace(",", ".")));
                product.setCarbohydrates(Double.parseDouble(list.get(3).replace(",", ".")));
                product.setCalories(Double.parseDouble(list.get(4).replace(",", ".")));
                App.getComponent().getProductRepository().writeToDB(String.valueOf(++index), product);
            }
        }
    }

    private boolean checkElement(Element element) {
        return element != null && element.hasText() && !element.text().equals("Продукты") && !element.text().equals("Вес (г)") && !element.text().equals("Белки") && !element.text().equals("Жиры") && !element.text().equals("Углеводы") && !element.text().equals("Калории");
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected Void doInBackground(Void... voids) {
        startParse();
        return null;
    }
}
