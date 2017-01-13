package com.touch.jbehave;

import net.serenitybdd.jbehave.SerenityStories;
import org.jbehave.core.io.StoryFinder;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AcceptanceTestSuite extends SerenityStories {
    @Override
    public List<String> storyPaths() {
        List<String> storyPath = new ArrayList<String>();
        String storyName = System.getProperty("story");
        if (storyName != null) {
            String[] stories = storyName.split(";");
            storyPath = new StoryFinder().findPaths(new File("").getAbsolutePath()+"\\src\\test\\resources\\", Arrays.asList(stories), null);
        } else {
            storyPath = super.storyPaths();
        }
        return storyPath;
    }
}