package ru.overtired.yamblz2017;

import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.contrib.DrawerActions;
import android.support.test.espresso.contrib.NavigationViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.EditText;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ru.overtired.yamblz2017.main_activity.MainActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyString;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule(MainActivity.class);


    @Test
    public void weatherFragmentShows() {
        onView(withId(R.id.main_activity_fragment_container)).check(matches(isDisplayed()));
    }

    @Test
    public void preferencesFragmentTest() throws InterruptedException {
        Thread.sleep(500);

        onView(withId(R.id.main_activity_drawer_layout)).perform(DrawerActions.open());
        onView(withId(R.id.main_activity_navigation_view))
                .perform(NavigationViewActions.navigateTo(R.id.main_activity_nav_settings));
        onView(withId(R.id.main_activity_drawer_layout)).perform(DrawerActions.close());

        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.list),
                        withParent(withId(android.R.id.list_container)),
                        isDisplayed()));
        recyclerView.perform(actionOnItemAtPosition(2, click()));

        onView(allOf(
                is(instanceOf(EditText.class))));

        onView(withId(R.id.enter_city_edittext)).check(matches((not(withText(isEmptyString())))));
    }
}
