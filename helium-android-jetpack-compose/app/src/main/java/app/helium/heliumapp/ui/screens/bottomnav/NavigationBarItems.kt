package app.helium.heliumapp.ui.screens.bottomnav

import app.helium.heliumapp.R


enum class NavigationBarItems(val icon: Int){
    Report(icon = R.drawable.siren),
    Notification(icon = R.drawable.microphone),
    Home(icon = R.drawable.appicon),
    Record(icon = R.drawable.nav),
    Help(icon = R.drawable.exclamation)
}

/* TODO-FIXME-CLEANUP
enum class NavigationBarItems(val icon: Int){
    Report(icon = R.drawable.rounded_rect),
    Notification(icon = R.drawable.outline_bell),
    Home(icon = R.drawable.appicon),
    Record(icon = R.drawable.calendar),
    Help(icon = R.drawable.gear)
}
*/