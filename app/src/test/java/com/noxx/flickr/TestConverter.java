package com.noxx.flickr;

import com.noxx.flickr.dtopackage.FlickrPhotosDto;
import com.noxx.flickr.dtopackage.FlickrResponseDto;
import com.noxx.flickr.dtopackage.PhotoDto;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by HB on 25/11/2016.
 */

public class TestConverter {

    @Test
    public void test () {


        List<PhotoDto> photoList = new ArrayList<>();
        photoList.add((new PhotoDto("31225264145","141919204@N05","4ba56ac1a6","5678","6","2017KX65AHF_Action_1","1","0","0")));
        photoList.add((new PhotoDto("30857854030","141919204@N05","2030f8894d","5743","6","2017KX65AHF_Action_2","1","0","0")));



        FlickrPhotosDto flickrPhotosDto = new FlickrPhotosDto("1","160887","2","321774", photoList);
        FlickrResponseDto flickrResponseDto = new FlickrResponseDto(flickrPhotosDto,"ok");

        List<Picture> expectedList = new ArrayList<>();
        expectedList.add(new Picture("2017KX65AHF_Action_1","https://farm6.static.flickr.com/5678/31225264145_4ba56ac1a6.jpg"));
        expectedList.add(new Picture("2017KX65AHF_Action_2","https://farm6.static.flickr.com/5743/30857854030_2030f8894d.jpg"));

        List<Picture> converterList = Converter.convert(flickrResponseDto);

        //à utiliser pour le debug => assertEquals(converterList.toString(),expectedList.toString());

        assertEquals(converterList,expectedList);
    }
}
