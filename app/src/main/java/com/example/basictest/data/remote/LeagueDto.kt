package com.example.basictest.data.remote

import com.google.gson.annotations.SerializedName

data class LeagueDto(
    @SerializedName("idLeague") val idLeague: String,
    @SerializedName("strLeague") val strLeague: String,
    @SerializedName("strSport") val strSport: String?,
    @SerializedName("strLeagueAlternate") val strLeagueAlternate: String? = null,
)

data class TeamDto(
    /*@SerializedName("idTeam") val idTeam: String,
    @SerializedName("strTeam") val strTeam: String,
    @SerializedName("strBadge") val strBadge: String,*/

    @SerializedName("idTeam") val idTeam: String,
    @SerializedName("idSoccerXML") val idSoccerXML: String? = null,
    @SerializedName("idAPIfootball") val idAPIfootball: String? = null,
    @SerializedName("intLoved") val intLoved: String? = null,
    @SerializedName("strTeam") val strTeam: String,
    @SerializedName("strTeamAlternate") val strTeamAlternate: String?= null,
    @SerializedName("strTeamShort") val strTeamShort: String?= null,
    @SerializedName("intFormedYear") val intFormedYear: String?= null,
    @SerializedName("strSport") val strSport: String?= null,
    @SerializedName("strLeague") val strLeague: String?= null,
    @SerializedName("idLeague") val idLeague: String?= null,
    @SerializedName("strLeague2") val strLeague2: String?= null,
    @SerializedName("idLeague2") val idLeague2: String?= null,
    @SerializedName("strLeague3") val strLeague3: String?= null,
    @SerializedName("idLeague3") val idLeague3: String?= null,
    @SerializedName("strLeague4") val strLeague4: String?= null,
    @SerializedName("idLeague4") val idLeague4: String?= null,
    @SerializedName("strLeague5") val strLeague5: String?= null,
    @SerializedName("idLeague5") val idLeague5: String?= null,
    @SerializedName("strLeague6") val strLeague6: String?= null,
    @SerializedName("idLeague6") val idLeague6: String?= null,
    @SerializedName("strLeague7") val strLeague7: String?= null,
    @SerializedName("idLeague7") val idLeague7: String?= null,
    @SerializedName("strDivision") val strDivision: String?= null,
    @SerializedName("idVenue") val idVenue: String?= null,
    @SerializedName("strStadium") val strStadium: String?= null,
    @SerializedName("strKeywords") val strKeywords: String?= null,
    @SerializedName("strRSS") val strRSS: String?= null,
    @SerializedName("strLocation") val strLocation: String?= null,
    @SerializedName("intStadiumCapacity") val intStadiumCapacity: String?= null,
    @SerializedName("strWebsite") val strWebsite: String?= null,
    @SerializedName("strFacebook") val strFacebook: String?= null,
    @SerializedName("strTwitter") val strTwitter: String?= null,
    @SerializedName("strInstagram") val strInstagram: String?= null,
    @SerializedName("strDescriptionEN") val strDescriptionEN: String?= null,
    @SerializedName("strDescriptionDE") val strDescriptionDE: String?= null,
    @SerializedName("strDescriptionFR") val strDescriptionFR: String?= null,
    @SerializedName("strDescriptionCN") val strDescriptionCN: String?= null,
    @SerializedName("strDescriptionIT") val strDescriptionIT: String?= null,
    @SerializedName("strDescriptionJP") val strDescriptionJP: String?= null,
    @SerializedName("strDescriptionRU") val strDescriptionRU: String?= null,
    @SerializedName("strDescriptionES") val strDescriptionES: String?= null,
    @SerializedName("strDescriptionPT") val strDescriptionPT: String?= null,
    @SerializedName("strDescriptionSE") val strDescriptionSE: String?= null,
    @SerializedName("strDescriptionNL") val strDescriptionNL: String?= null,
    @SerializedName("strDescriptionHU") val strDescriptionHU: String?= null,
    @SerializedName("strDescriptionNO") val strDescriptionNO: String?= null,
    @SerializedName("strDescriptionIL") val strDescriptionIL: String?= null,
    @SerializedName("strDescriptionPL") val strDescriptionPL: String?= null,
    @SerializedName("strColour1") val strColour1: String?= null,
    @SerializedName("strColour2") val strColour2: String?= null,
    @SerializedName("strColour3") val strColour3: String?= null,
    @SerializedName("strGender") val strGender: String?= null,
    @SerializedName("strCountry") val strCountry: String?= null,
    @SerializedName("strBadge") val strBadge: String,
    @SerializedName("strLogo") val strLogo: String?= null,
    @SerializedName("strFanart1") val strFanart1: String?= null,
    @SerializedName("strFanart2") val strFanart2: String?= null,
    @SerializedName("strFanart3") val strFanart3: String?= null,
    @SerializedName("strFanart4") val strFanart4: String?= null,
    @SerializedName("strBanner") val strBanner: String?= null,
    @SerializedName("strEquipment") val strEquipment: String?= null,
    @SerializedName("strYoutube") val strYoutube: String?= null,
    @SerializedName("strLocked") val strLocked: String?= null,


)

data class LeaguesResponse(
    @SerializedName("leagues") val leagues: List<LeagueDto>,
)

data class TeamsResponse(
    @SerializedName("teams") val teams: List<TeamDto>,
)
