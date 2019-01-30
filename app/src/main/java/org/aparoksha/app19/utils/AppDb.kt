package org.aparoksha.app19.utils

import android.content.Context
import net.rehacktive.waspdb.WaspDb
import net.rehacktive.waspdb.WaspFactory
import org.aparoksha.app19.models.Competition
import org.aparoksha.app19.models.Person
import org.aparoksha.app19.models.Sponsor
import org.aparoksha.app19.models.Workshop

class AppDB private constructor(context: Context) {
    private val waspDB: WaspDb = WaspFactory.openOrCreateDatabase(
        context.filesDir.path,
        "eventDB",
        "aparoksha18"
    )

    private val eventHash = waspDB.openOrCreateHash("events")
    private val bookmarksHash = waspDB.openOrCreateHash("bookmarks")
    private val teamHash = waspDB.openOrCreateHash("team")
    private val developerHash = waspDB.openOrCreateHash("developer")
    private val sponsorHash = waspDB.openOrCreateHash("sponsors")
    private val flagshipHash = waspDB.openOrCreateHash("flagships")
    private val competitionHash = waspDB.openOrCreateHash("apkMonthCompetitions")
    private val workshopHash = waspDB.openOrCreateHash("apkMonthWorkshops")

    companion object : SingletonHolder<AppDB, Context>(::AppDB)

//    fun getAllEvents(): List<Event> = eventHash.getAllValues<Event>()

//    fun getAllFlagships(): List<FlagshipEvents> = eventHash.getAllValues<FlagshipEvents>()

    fun getAllTeamMembers() = teamHash.getAllValues<Person>().sortedBy { it.id }

//    fun getAllDeveloperMembers() = developerHash.getAllValues<Developer>().sortedBy { it.id }

/*
    fun getEventsOfCategory(category: String) = eventHash.getAllValues<Event>()
            .filter { it.categories.contains(category) }
            .sortedBy { it.timestamp }
*/

//    fun getBookmarkedEvents(): MutableList<Event> = bookmarksHash.getAllValues<Event>()

//    fun addBookmark(id: Long): Boolean = bookmarksHash.put(id, getEventByID(id))

    fun removeBookmark(id: Long): Boolean = bookmarksHash.remove(id)

//    fun isBookmarked(id: Long) = (bookmarksHash.get<Event>(id) != null)

//    fun getEventByID(id: Long): Event = eventHash.get<Event>(id)

//    fun storeEvents(events: List<Event>) = events.forEach { eventHash.put(it.id, it) }

//    fun storeFlagships(events: List<FlagshipEvents>) = events.forEach { flagshipHash.put(it.id, it) }

    fun getAllSponsors(): MutableList<Sponsor> = sponsorHash.getAllValues<Sponsor>()

    fun storeSponsors(sponsors: List<Sponsor>) = sponsors.forEach { sponsorHash.put(it.id, it) }

    fun storeTeam(teamList: List<Person>) = teamList.forEach { teamHash.put(it.id, it) }

    fun storeCompetitions(competitionList: List<Competition>) = competitionList.forEach { competitionHash.put(it.id, it) }

    fun getAllCompetitions(): List<Competition> = competitionHash.getAllValues<Competition>().sortedBy { it.dateTime }

    fun storeWorkshops(workshopList: List<Workshop>) = workshopList.forEach { workshopHash.put(it.id, it) }

    fun getAllWorkshops(): List<Workshop> = workshopHash.getAllValues<Workshop>().sortedBy { it.title }

//    fun storeDevelopers(developers: List<Developer>) = developers.forEach({developerHash.put(it.id,it)})

}