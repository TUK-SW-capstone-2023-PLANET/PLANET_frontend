package com.example.planet.data.remote.api.spring

import com.example.planet.data.remote.dto.Advertisement
import com.example.planet.data.remote.dto.ImageUrl
import com.example.planet.data.remote.dto.response.plogging.PloggingComplete
import com.example.planet.data.remote.dto.request.plogging.TrashImageUrlInfo
import com.example.planet.data.remote.dto.request.plogging.PloggingInfo
import com.example.planet.data.remote.dto.Tier
import com.example.planet.data.remote.dto.TrashCan
import com.example.planet.data.remote.dto.request.signin.LoginInfo
import com.example.planet.data.remote.dto.request.signup.PlanetUser
import com.example.planet.data.remote.dto.response.plogging.PloggingId
import com.example.planet.data.remote.dto.response.plogging.PloggingResult
import com.example.planet.data.remote.dto.response.plogging.Trash
import com.example.planet.data.remote.dto.response.signup.SignUpResponse
import com.example.planet.data.remote.dto.response.ranking.planet.HigherPlanetUser
import com.example.planet.data.remote.dto.response.ranking.planet.PagingPlanetUser
import com.example.planet.data.remote.dto.response.ranking.planet.PlanetRankingUser
import com.example.planet.data.remote.dto.response.ranking.season.PagingSeasonUser
import com.example.planet.data.remote.dto.response.ranking.season.SeasonUser
import com.example.planet.data.remote.dto.response.ranking.university.PagingUniversity
import com.example.planet.data.remote.dto.response.ranking.university.University
import com.example.planet.data.remote.dto.response.ranking.universityuser.ExpandedUniversityUser
import com.example.planet.data.remote.dto.response.ranking.universityuser.PagingUniversityUser
import com.example.planet.data.remote.dto.response.ranking.universityuser.UniversityUser
import com.example.planet.data.remote.dto.response.signin.LoginResponse
import com.example.planet.data.remote.dto.response.signup.UserId
import com.example.planet.data.remote.dto.response.user.UserInfo
import com.example.planet.data.remote.dto.response.user.UserInfoResponse
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface MainApi {
    @GET("/trash-can/all")
    suspend fun getAllTrashCanLocation(): List<TrashCan>

    @GET("/plogging/user/{userId}")
    suspend fun getPloggingId(@Path("userId") userId: Int): PloggingId

    @Multipart
    @POST("/image")
    suspend fun postImage(@Part file: MultipartBody.Part): ImageUrl

    @POST("/plogging-live")
    suspend fun postPloggingLive(@Body trashImageUrlInfo: TrashImageUrlInfo): List<Trash>

    @POST("/plogging")
    suspend fun postPlogging(@Body ploggingInfo: PloggingInfo): PloggingComplete


    @GET("/plogging/{ploggingId}")
    suspend fun getPloggingInfo(@Path("ploggingId") ploggingId: Int): PloggingResult

    @GET("/advertisement")
    suspend fun getTopBanner(): List<Advertisement>

    // University 관련 -----------------------------------------------------------------------------
    // 대학교 랭킹 3개 조회
    @GET("/university/rank")
    suspend fun getHigherUniversities(): List<University>

    // 나의 대학교 랭킹 조회
    @GET("/university/rank/user/{userId}")
    suspend fun getMyUniversityInfo(@Path("userId") userId: Int): University


    // 대학교 랭킹 전체 조회
    @GET("/university/rank/all")
    suspend fun getAllUniversityRanking(@Query("page") page: Int): PagingUniversity

    // University User 관련 -------------------------------------------------------------------------
    // 자대 대학교 유저 랭킹 전체 조회
    @GET("/user/{userId}/rank/all/university")
    suspend fun getAllUniversityUserRanking(
        @Path("userId") userId: Int = 0,
        @Query("page") page: Int
    ): PagingUniversityUser

    // 자대 대학교 유저 랭킹 상위 4명 조회
    @GET("/user/{userId}/rank/university/4")
    suspend fun getUniversityTop4UserRanking(@Path("userId") userId: Int): List<Map<Int, ExpandedUniversityUser>>

    // 자대 대학교 나의 랭킹 정보 조회
    @GET("/user/{userId}/rank/university")
    suspend fun getUniversityMyRanking(@Path("userId") userId: Int): UniversityUser


    // Season 관련 ---------------------------------------------------------------------------------
    // 나의 시즌 랭킹 정보 조회
    @GET("/season/user/{userId}/rank")
    suspend fun getMySeasonRanking(@Path("userId") userId: Int): SeasonUser

    // 시즌 유저 랭킹 5명 조회
    @GET("/season/user/{userId}/rank/5")
    suspend fun getSeasonTop5UserInfo(@Path("userId") userId: Int): List<Map<Int, SeasonUser>>

    // 시즌 유저 랭킹 전체 조회
    @GET("/season/rank/all")
    suspend fun getAllSeasonRanking(@Query("page") page: Int): PagingSeasonUser


    // Planet User 관련 ----------------------------------------------------------------------------
    // 플래넷 유저 랭킹 상위 3명 조회
    @GET("/user/rank")
    suspend fun getTop3PlanetUser(): List<HigherPlanetUser>

    // 나의 시즌 랭킹 정보 조회
    @GET("/user/{userId}/rank")
    suspend fun getMyPlanetRanking(@Path("userId") userId: Int): PlanetRankingUser

    // 플래넷 유저 랭킹 전체 조회
    @GET("/user/rank/all")
    suspend fun getAllPlanetUserRanking(@Query("page") page: Int): PagingPlanetUser

    @GET("/tier")
    suspend fun getTierList(): List<Tier>

    // 유저 정보 조회
    @GET("/user/{userId}")
    suspend fun getUserInfo(@Path("userId")userId: Int ): UserInfo

    // 유저 정보 수정 TODO(UserInfoResponse 수정하면 UserId로 바꿀 것)
    @PUT("/user")
    suspend fun putUserInfo(@Body userInfo: UserInfo): UserInfoResponse

    // SignUpActivity 관련  ------------------------------------------------------------------------
    @GET("/login/check")
    suspend fun getUniversityCheck(@Query("name") name: String): SignUpResponse

    @GET("/login")
    suspend fun getAuthCode(
        @Query("name") name: String,
        @Query("email") email: String
    ): SignUpResponse

    @GET("/login/code")
    suspend fun getAuthCodeCheck(
        @Query("code") code: String,
        @Query("name") name: String,
        @Query("email") email: String
    ): SignUpResponse

    @GET("/user/name")
    suspend fun getDuplicatedNameCheck(@Query("name") name: String): Boolean

    @POST("/user")
    suspend fun postUserInfo(@Body user: PlanetUser): UserId

    @POST("/login/user")
    suspend fun postLogin(@Body loginInfo: LoginInfo): LoginResponse

}