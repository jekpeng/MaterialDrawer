package com.mikepenz.materialdrawer.app

import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import com.mikepenz.aboutlibraries.LibsBuilder
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.iconics.typeface.library.fontawesome.FontAwesome
import com.mikepenz.iconics.typeface.library.googlematerial.GoogleMaterial
import com.mikepenz.iconics.utils.actionBar
import com.mikepenz.iconics.utils.paddingDp
import com.mikepenz.materialdrawer.holder.BadgeStyle
import com.mikepenz.materialdrawer.holder.ColorHolder
import com.mikepenz.materialdrawer.holder.StringHolder
import com.mikepenz.materialdrawer.iconics.iconicsIcon
import com.mikepenz.materialdrawer.iconics.withIcon
import com.mikepenz.materialdrawer.model.*
import com.mikepenz.materialdrawer.model.interfaces.*
import com.mikepenz.materialdrawer.model.utils.descriptionRes
import com.mikepenz.materialdrawer.model.utils.nameRes
import com.mikepenz.materialdrawer.model.utils.nameText
import com.mikepenz.materialdrawer.util.addItems
import com.mikepenz.materialdrawer.util.updateBadge
import com.mikepenz.materialdrawer.widget.AccountHeaderView
import kotlinx.android.synthetic.main.activity_sample.*


class DrawerActivity : AppCompatActivity() {

    private lateinit var headerView: AccountHeaderView
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sample)

        // Handle Toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        actionBarDrawerToggle = ActionBarDrawerToggle(this, root, toolbar, com.mikepenz.materialdrawer.R.string.material_drawer_open, com.mikepenz.materialdrawer.R.string.material_drawer_close)

        // Create a few sample profile
        // NOTE you have to define the loader logic too. See the CustomApplication for more details
        val profile = ProfileDrawerItem().withName("Mike Penz").withEmail("mikepenz@gmail.com").withIcon("https://avatars3.githubusercontent.com/u/1476232?v=3&s=460").withIdentifier(100)
        val profile2 = ProfileDrawerItem().withName("Demo User").withEmail("demo@github.com").withIcon("https://avatars2.githubusercontent.com/u/3597376?v=3&s=460").withIdentifier(101)
        val profile3 = ProfileDrawerItem().withName("Max Muster").withEmail("max.mustermann@gmail.com").withIcon(R.drawable.profile2).withIdentifier(102)
        val profile4 = ProfileDrawerItem().withName("Felix House").withEmail("felix.house@gmail.com").withIcon(R.drawable.profile3).withIdentifier(103)
        val profile5 = ProfileDrawerItem().withName("Mr. X").withEmail("mister.x.super@gmail.com").withIcon(R.drawable.profile4).withIdentifier(104)
        val profile6 = ProfileDrawerItem().withName("Batman").withEmail("batman@gmail.com").withIcon(R.drawable.profile5).withIdentifier(105).withBadge("123").withBadgeStyle(BadgeStyle().apply {
            textColor = ColorHolder.fromColor(Color.BLACK)
            color = ColorHolder.fromColor(Color.WHITE)
        })

        // Create the AccountHeader
        headerView = AccountHeaderView(this).apply {
            attachToSliderView(slider)
            addProfiles(
                    profile,
                    profile2,
                    profile3,
                    profile4,
                    profile5,
                    profile6,
                    //don't ask but google uses 14dp for the add account icon in gmail but 20dp for the normal icons (like manage account)
                    ProfileSettingDrawerItem().withName("Add Account").withDescription("Add new GitHub Account").withIcon(IconicsDrawable(context, GoogleMaterial.Icon.gmd_add).apply { actionBar(); paddingDp = 5 }.mutate()).withIconTinted(true).withIdentifier(PROFILE_SETTING.toLong()),
                    ProfileSettingDrawerItem().withName("Manage Account").withIcon(GoogleMaterial.Icon.gmd_settings).withIdentifier(100001)
            )
            onAccountHeaderListener = { view, profile, current ->
                //sample usage of the onProfileChanged listener
                //if the clicked item has the identifier 1 add a new profile ;)
                if (profile is IDrawerItem<*> && profile.identifier == PROFILE_SETTING.toLong()) {
                    val count = 100 + (profiles?.size ?: 0) + 1
                    val newProfile = ProfileDrawerItem().withNameShown(true).withName("Batman$count").withEmail("batman$count@gmail.com").withIcon(R.drawable.profile5).withIdentifier(count.toLong())
                    profiles?.let {
                        //we know that there are 2 setting elements. set the new profile above them ;)
                        addProfile(newProfile, it.size - 2)
                    } ?: addProfiles(newProfile)
                }

                //false if you have not consumed the event and it should close the drawer
                false
            }
            withSavedInstance(savedInstanceState)
        }

        slider.apply {
            addItems(
                    PrimaryDrawerItem().apply { nameRes = R.string.drawer_item_compact_header; descriptionRes = R.string.drawer_item_compact_header_desc; iconicsIcon = GoogleMaterial.Icon.gmd_brightness_5; isSelectable = false; identifier = 1 },
                    PrimaryDrawerItem().apply { nameRes = R.string.drawer_item_action_bar_drawer; descriptionRes = R.string.drawer_item_action_bar_drawer_desc; iconicsIcon = FontAwesome.Icon.faw_home; isSelectable = false; identifier = 2 },
                    PrimaryDrawerItem().apply { nameRes = R.string.drawer_item_multi_drawer; descriptionRes = R.string.drawer_item_multi_drawer_desc; iconicsIcon = FontAwesome.Icon.faw_gamepad; isSelectable = false; identifier = 3 },
                    PrimaryDrawerItem().apply { nameRes = R.string.drawer_item_advanced_drawer; descriptionRes = R.string.drawer_item_advanced_drawer_desc; iconicsIcon = GoogleMaterial.Icon.gmd_adb; isSelectable = false; identifier = 5 },
                    PrimaryDrawerItem().apply { nameRes = R.string.drawer_item_embedded_drawer; descriptionRes = R.string.drawer_item_embedded_drawer_desc; iconicsIcon = GoogleMaterial.Icon.gmd_battery_full; isSelectable = false; identifier = 7 },
                    PrimaryDrawerItem().apply { nameRes = R.string.drawer_item_fullscreen_drawer; descriptionRes = R.string.drawer_item_fullscreen_drawer_desc; iconicsIcon = GoogleMaterial.Icon.gmd_label; isSelectable = false; identifier = 8 },
                    PrimaryDrawerItem().apply { nameRes = R.string.drawer_item_menu_drawer; descriptionRes = R.string.drawer_item_menu_drawer_desc; iconicsIcon = GoogleMaterial.Icon.gmd_filter_list; isSelectable = false; identifier = 10 },
                    PrimaryDrawerItem().apply { nameRes = R.string.drawer_item_mini_drawer; descriptionRes = R.string.drawer_item_mini_drawer_desc; iconicsIcon = GoogleMaterial.Icon.gmd_battery_charging_full; isSelectable = false; identifier = 11 },
                    PrimaryDrawerItem().apply { nameRes = R.string.drawer_item_fragment_drawer; descriptionRes = R.string.drawer_item_fragment_drawer_desc; iconicsIcon = GoogleMaterial.Icon.gmd_disc_full; isSelectable = false; identifier = 12 },
                    PrimaryDrawerItem().apply { nameRes = R.string.drawer_item_collapsing_toolbar_drawer; descriptionRes = R.string.drawer_item_collapsing_toolbar_drawer_desc; iconicsIcon = GoogleMaterial.Icon.gmd_camera_rear; isSelectable = false; identifier = 13 },
                    PrimaryDrawerItem().apply { nameRes = R.string.drawer_item_persistent_compact_header; descriptionRes = R.string.drawer_item_persistent_compact_header_desc; iconicsIcon = GoogleMaterial.Icon.gmd_brightness_5; isSelectable = false; identifier = 14 },
                    PrimaryDrawerItem().apply { nameRes = R.string.drawer_item_crossfade_drawer_layout_drawer; descriptionRes = R.string.drawer_item_crossfade_drawer_layout_drawer_desc; iconicsIcon = GoogleMaterial.Icon.gmd_format_bold; isSelectable = false; identifier = 15 },
                    PrimaryDrawerItem().apply { nameRes = R.string.drawer_item_navigation_drawer; descriptionRes = R.string.drawer_item_navigation_drawer_desc; iconicsIcon = GoogleMaterial.Icon.gmd_navigation; isSelectable = false; identifier = 1305 },
                    ExpandableBadgeDrawerItem().apply {
                        nameText = "Collapsable Badge"; iconicsIcon = GoogleMaterial.Icon.gmd_format_bold; identifier = 18; isSelectable = false; badge = StringHolder("100")
                        badgeStyle = BadgeStyle().apply { textColor = ColorHolder.fromColor(Color.WHITE); color = ColorHolder.fromColorRes(R.color.md_red_700) }
                        withSubItems(
                                SecondaryDrawerItem().withName("CollapsableItem").withLevel(2).withIcon(GoogleMaterial.Icon.gmd_format_bold).withIdentifier(2000),
                                SecondaryDrawerItem().withName("CollapsableItem 2").withLevel(2).withIcon(GoogleMaterial.Icon.gmd_format_bold).withIdentifier(2001)
                        )
                    },
                    ExpandableDrawerItem().withName("Collapsable").withIcon(GoogleMaterial.Icon.gmd_filter_list).withIdentifier(19).withSelectable(false).withSubItems(
                            SecondaryDrawerItem().withName("CollapsableItem").withLevel(2).withIcon(GoogleMaterial.Icon.gmd_filter_list).withIdentifier(2002),
                            SecondaryDrawerItem().withName("CollapsableItem 2").withLevel(2).withIcon(GoogleMaterial.Icon.gmd_filter_list).withIdentifier(2003)
                    ),
                    SectionDrawerItem().withName(R.string.drawer_item_section_header),
                    SecondaryDrawerItem().withName(R.string.drawer_item_open_source).withIcon(FontAwesome.Icon.faw_github).withIdentifier(20).withSelectable(false),
                    SecondaryDrawerItem().withName(R.string.drawer_item_contact).withIcon(GoogleMaterial.Icon.gmd_format_color_fill).withIdentifier(21).withTag("Bullhorn")
                    /*,
                    DividerDrawerItem ()
                    SwitchDrawerItem ().withName("Switch").withIcon(Octicons.Icon.oct_tools).withChecked(true).withOnCheckedChangeListener(onCheckedChangeListener)
                    SwitchDrawerItem ().withName("Switch2").withIcon(Octicons.Icon.oct_tools).withChecked(true).withOnCheckedChangeListener(onCheckedChangeListener).withSelectable(false)
                    ToggleDrawerItem ().withName("Toggle").withIcon(Octicons.Icon.oct_tools).withChecked(true).withOnCheckedChangeListener(onCheckedChangeListener)
                    DividerDrawerItem ()
                    SecondarySwitchDrawerItem ().withName("Secondary switch").withIcon(Octicons.Icon.oct_tools).withChecked(true).withOnCheckedChangeListener(onCheckedChangeListener)
                    SecondarySwitchDrawerItem ().withName("Secondary Switch2").withIcon(Octicons.Icon.oct_tools).withChecked(true).withOnCheckedChangeListener(onCheckedChangeListener).withSelectable(false)
                    SecondaryToggleDrawerItem ().withName("Secondary toggle").withIcon(Octicons.Icon.oct_tools).withChecked(true).withOnCheckedChangeListener(onCheckedChangeListener)
                    */
            )
            onDrawerItemClickListener = { v, drawerItem, position ->
                //check if the drawerItem is set.
                //there are different reasons for the drawerItem to be null
                //--> click on the header
                //--> click on the footer
                //those items don't contain a drawerItem

                var intent: Intent? = null
                when {
                    drawerItem.identifier == 1L -> intent = Intent(this@DrawerActivity, CompactHeaderDrawerActivity::class.java)
                    drawerItem.identifier == 2L -> intent = Intent(this@DrawerActivity, ActionBarActivity::class.java)
                    drawerItem.identifier == 3L -> intent = Intent(this@DrawerActivity, MultiDrawerActivity::class.java)
                    drawerItem.identifier == 5L -> intent = Intent(this@DrawerActivity, AdvancedActivity::class.java)
                    drawerItem.identifier == 7L -> intent = Intent(this@DrawerActivity, EmbeddedDrawerActivity::class.java)
                    drawerItem.identifier == 8L -> intent = Intent(this@DrawerActivity, FullscreenDrawerActivity::class.java)
                    drawerItem.identifier == 10L -> intent = Intent(this@DrawerActivity, MenuDrawerActivity::class.java)
                    drawerItem.identifier == 11L -> intent = Intent(this@DrawerActivity, MiniDrawerActivity::class.java)
                    drawerItem.identifier == 12L -> intent = Intent(this@DrawerActivity, FragmentActivity::class.java)
                    drawerItem.identifier == 13L -> intent = Intent(this@DrawerActivity, CollapsingToolbarActivity::class.java)
                    drawerItem.identifier == 14L -> intent = Intent(this@DrawerActivity, PersistentDrawerActivity::class.java)
                    drawerItem.identifier == 15L -> intent = Intent(this@DrawerActivity, CrossfadeDrawerLayoutActvitiy::class.java)
                    drawerItem.identifier == 1305L -> intent = Intent(this@DrawerActivity, NavControllerActivity::class.java)
                    drawerItem.identifier == 20L -> intent = LibsBuilder()
                            .withFields(R.string::class.java.fields)
                            .withLicenseShown(true)
                            .intent(this@DrawerActivity)
                }
                if (intent != null) {
                    this@DrawerActivity.startActivity(intent)
                }

                false

            }
            setSavedInstance(savedInstanceState)
        }

        //slider.withStickyHeader(R.layout.header)

        //slider.addStickyDrawerItems(
        //        SecondaryDrawerItem().withName(R.string.drawer_item_settings).withIcon(FontAwesome.Icon.faw_cog).withIdentifier(10),
        //        SecondaryDrawerItem().withName(R.string.drawer_item_open_source).withIcon(FontAwesome.Icon.faw_github)
        //)

        //only set the active selection or active profile if we do not recreate the activity
        if (savedInstanceState == null) {
            // set the selection to the item with the identifier 11
            slider.setSelection(21, false)

            //set the active profile
            headerView.activeProfile = profile3
        }

        slider.updateBadge(4, StringHolder(10.toString() + ""))
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        actionBarDrawerToggle.onConfigurationChanged(newConfig)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        actionBarDrawerToggle.syncState()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSaveInstanceState(_outState: Bundle) {
        var outState = _outState
        //add the values which need to be saved from the drawer to the bundle
        outState = slider.saveInstanceState(outState)

        //add the values which need to be saved from the accountHeader to the bundle
        outState = headerView.saveInstanceState(outState)
        super.onSaveInstanceState(outState)
    }


    override fun onBackPressed() {
        //handle the back press :D close the drawer first and if the drawer is closed close the activity
        if (root.isDrawerOpen(slider)) {
            root.closeDrawer(slider)
        } else {
            super.onBackPressed()
        }
    }

    companion object {
        private const val PROFILE_SETTING = 100000
    }
}
