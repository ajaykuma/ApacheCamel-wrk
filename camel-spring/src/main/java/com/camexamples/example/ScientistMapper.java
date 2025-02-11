package com.camexamples.example;

import com.camexamples.example.Scientist;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScientistMapper {

    public Map<String, Object> getMap(Scientist scientist) {
        Map<String, Object> answer = new HashMap<String, Object>();
        answer.put("scId", scientist.getscId());
        answer.put("scName", scientist.getscName());
        return answer;
    }
    public List<Scientist> readScientists(List<Map<String, String>> dataList) {

        System.out.println("data:" + dataList);

        List<Scientist> scientists = new ArrayList<Scientist>();

        for (Map<String, String> data : dataList) {

            Scientist scientist = new Scientist();

            scientist.setscId(data.get("scId"));
            scientist.setscName(data.get("scName"));
            scientists.add(scientist);
        }

        return scientists;
    }
}
