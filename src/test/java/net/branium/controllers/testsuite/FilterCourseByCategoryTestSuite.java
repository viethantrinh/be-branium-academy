package net.branium.controllers.testsuite;


import org.junit.platform.suite.api.IncludeTags;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectPackages({
        "net.branium"
})
@IncludeTags({
        "filter-course-by-category"
})
public class FilterCourseByCategoryTestSuite {
}
