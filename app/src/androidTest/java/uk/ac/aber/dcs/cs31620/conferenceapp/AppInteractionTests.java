package uk.ac.aber.dcs.cs31620.conferenceapp;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import uk.ac.aber.dcs.cs31620.conferenceapp.model.DataService;
import uk.ac.aber.dcs.cs31620.conferenceapp.model.Speaker;
import uk.ac.aber.dcs.cs31620.conferenceapp.ui.MainActivity;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.swipeLeft;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.TestCase.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class AppInteractionTests {

    @Rule
    public ActivityTestRule<MainActivity> activityRule
            = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void test_eventDetailsShowsFromRecyclerView() {
        onView(ViewMatchers.withId(R.id.eventRecyclerView))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        // Test correct details show
        onView(ViewMatchers.withId(R.id.eventDetailDetails)).check(matches(withText(
                "ARKit is the hot new framework announced at WWDC 2017. This workshop will get" +
                        " your feet wet so that you can create the augmented reality application" +
                        " of your dreams. ")));

        // Test Speaker shows correct
        onView(ViewMatchers.withId(R.id.eventDetailSpeakerGridSpeakerName)).check(matches(withText(
                "Speaker: Janie Clayton")));

        // Test Location shows correct
        onView(ViewMatchers.withId(R.id.eventDetailLocation)).check(matches(withText(
                "Location: Llandinam B23")));

        // Test Time shoaveTabStillObservesFavouriteListWhenActivityHasChangedPreviouslyws correct
        onView(ViewMatchers.withId(R.id.eventDetailTime)).check(matches(withText(
                "Time: 2019-12-10, 16:00 - 18:00")));

        // Test map is shown
        onView(ViewMatchers.withId(R.id.eventDetailsMap)).check(matches(isDisplayed()));
    }

    @Test
    public void test_speakerDetailsActivityForItem0() {
        // Get to speaker fragment
        onView(ViewMatchers.withId(R.id.pager)).perform(swipeLeft());
        onView(ViewMatchers.withId(R.id.pager)).perform(swipeLeft());

        // Click on the first speaker in the list to show the activity
        onView(ViewMatchers.withId(R.id.speakerRecyclerView)).perform(RecyclerViewActions
                .actionOnItemAtPosition(0, click()));

        // Check the details
        onView(ViewMatchers.withId(R.id.speakerActivitySpeakerDetails)).check(matches(withText(
                "Adam Rush is a passionate iOS developer with over 6 years commercial experience" +
                        ", contracting all over the UK & Europe. He's a tech addict and #Swift" +
                        " enthusiast.")));

        // Check the speaker name
        onView(ViewMatchers.withId(R.id.speakerActivitySpeakerName)).check(matches(withText(
                "Adam Rush")));

        // Check the twitter details
        onView(ViewMatchers.withId(R.id.speakerActivityTwitterHandle)).check(matches(withText(
                "adam9rush")));

        // Check the image view displays
        onView(ViewMatchers.withId(R.id.speakerActivitySpeakerImage)).check(matches(isDisplayed()));
    }

    @Test
    public void test_speakerDetailsActivityForItem1() {
        // Get to speaker fragment
        onView(ViewMatchers.withId(R.id.pager)).perform(swipeLeft());
        onView(ViewMatchers.withId(R.id.pager)).perform(swipeLeft());

        // Click on the first speaker in the list to show the activity
        onView(ViewMatchers.withId(R.id.speakerRecyclerView)).perform(
                RecyclerViewActions.actionOnItemAtPosition(1, click()));

        // Check the details
        onView(ViewMatchers.withId(R.id.speakerActivitySpeakerDetails)).check(matches(withText(
                "Agnieszka is a UX designer at Polidea.")));

        // Check the speaker name
        onView(ViewMatchers.withId(R.id.speakerActivitySpeakerName)).check(matches(withText(
                "Agnieszka  Czyak")));

        // Check the twitter details
        onView(ViewMatchers.withId(R.id.speakerActivityTwitterHandle)).check(matches(withText(
                "agaczyzak")));

        // Check the image view displays
        onView(ViewMatchers.withId(R.id.speakerActivitySpeakerImage)).check(matches(isDisplayed()));
    }

    @Test
    public void test_dataBaseLoadedSuccessfully() {
        DataService ds = DataService.getDataService();

        Speaker speaker = ds.getSpeaker("AdamRush");
        assertEquals("AdamRush", speaker.getSpeakerId());
        assertEquals("Adam Rush", speaker.getSpeakerName());
        assertEquals("adam9rush", speaker.getTwitterHandle());
    }
}