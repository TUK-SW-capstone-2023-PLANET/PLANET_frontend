package com.example.planet.data.remote.api.spring

import com.example.planet.data.remote.dto.Advertisement
import com.example.planet.data.remote.dto.ImageUrl
import com.example.planet.data.remote.dto.Tier
import com.example.planet.data.remote.dto.TrashCan
import com.example.planet.data.remote.dto.request.chat.ChatRoomId
import com.example.planet.data.remote.dto.request.chat.ChatSave
import com.example.planet.data.remote.dto.request.map.TrashCanImage
import com.example.planet.data.remote.dto.request.plogging.PloggingInfo
import com.example.planet.data.remote.dto.request.plogging.TrashImageUrlInfo
import com.example.planet.data.remote.dto.request.post.CommentId
import com.example.planet.data.remote.dto.request.post.CommentRequest
import com.example.planet.data.remote.dto.request.post.PostId
import com.example.planet.data.remote.dto.request.post.PostingInfo
import com.example.planet.data.remote.dto.request.signin.LoginInfo
import com.example.planet.data.remote.dto.request.signup.PlanetUser
import com.example.planet.data.remote.dto.response.chat.ChatInfoResponse
import com.example.planet.data.remote.dto.response.chat.ChatResponse
import com.example.planet.data.remote.dto.response.chat.ChatroomInfo
import com.example.planet.data.remote.dto.response.map.SearchPlace
import com.example.planet.data.remote.dto.response.map.TrashCanResponse
import com.example.planet.data.remote.dto.response.plogging.PloggingComplete
import com.example.planet.data.remote.dto.response.plogging.PloggingDayInfo
import com.example.planet.data.remote.dto.response.plogging.PloggingId
import com.example.planet.data.remote.dto.response.plogging.PloggingResult
import com.example.planet.data.remote.dto.response.plogging.Trash
import com.example.planet.data.remote.dto.response.post.CommentInfo
import com.example.planet.data.remote.dto.response.post.CommentResponse
import com.example.planet.data.remote.dto.response.post.HotPosted
import com.example.planet.data.remote.dto.response.post.MyPostedInfo
import com.example.planet.data.remote.dto.response.post.PopularPostedInfo
import com.example.planet.data.remote.dto.response.post.PostResponse
import com.example.planet.data.remote.dto.response.post.Posted
import com.example.planet.data.remote.dto.response.post.PostedInfo
import com.example.planet.data.remote.dto.response.post.ViewPosted
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
import com.example.planet.data.remote.dto.response.signup.SignUpResponse
import com.example.planet.data.remote.dto.response.signup.UserId
import com.example.planet.data.remote.dto.response.statistics.StatisticsPloggingInfo
import com.example.planet.data.remote.dto.response.user.UserInfo
import com.example.planet.data.remote.dto.response.user.UserInfoResponse
import com.example.planet.data.remote.dto.response.user.UserUniversityInfo
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface MainApi {

    // Plogging 관련 -----------------------------------------------------------------------------
    @GET("/trash-can/all")
    suspend fun getAllTrashCanLocation(): List<TrashCan>

    @GET("/plogging/user/{userId}")
    suspend fun getPloggingId(@Path("userId") userId: Long): PloggingId

    @Multipart
    @POST("/image")
    suspend fun postImage(@Part file: MultipartBody.Part): ImageUrl

    @Multipart
    @POST("/images")
    suspend fun postImages(@Part files: List<MultipartBody.Part>): List<ImageUrl>

    @POST("/plogging-live")
    suspend fun postPloggingLive(@Body trashImageUrlInfo: TrashImageUrlInfo): List<Trash>

    @POST("/plogging")
    suspend fun postPlogging(@Body ploggingInfo: PloggingInfo): PloggingComplete


    @GET("/plogging/{ploggingId}")
    suspend fun getPloggingInfo(@Path("ploggingId") ploggingId: Long): PloggingResult

    @GET("/plogging/user/{userId}/year/{year}/month/{month}")
    suspend fun getPloggingActiveList(
        @Path("userId") userId: Long,
        @Path("year") year: Int,
        @Path("month") month: Int
    ): List<Map<Int, List<PloggingDayInfo>>>

    @GET("/advertisement")
    suspend fun getTopBanner(): List<Advertisement>

    // University 관련 -----------------------------------------------------------------------------
    // 대학교 랭킹 3개 조회
    @GET("/university/rank")
    suspend fun getHigherUniversities(): List<University>

    // 나의 대학교 랭킹 조회
    @GET("/university/rank/user/{userId}")
    suspend fun getMyUniversityInfo(@Path("userId") userId: Long): University


    // 대학교 랭킹 전체 조회
    @GET("/university/rank/all")
    suspend fun getAllUniversityRanking(@Query("page") page: Int): PagingUniversity

    // University User 관련 -------------------------------------------------------------------------
    // 자대 대학교 유저 랭킹 전체 조회
    @GET("/user/{userId}/rank/all/university")
    suspend fun getAllUniversityUserRanking(
        @Path("userId") userId: Long = 0,
        @Query("page") page: Int
    ): PagingUniversityUser

    // 자대 대학교 유저 랭킹 상위 4명 조회
    @GET("/user/{userId}/rank/university/4")
    suspend fun getUniversityTop4UserRanking(@Path("userId") userId: Long): List<Map<Int, ExpandedUniversityUser>>

    // 자대 대학교 나의 랭킹 정보 조회
    @GET("/user/{userId}/rank/university")
    suspend fun getUniversityMyRanking(@Path("userId") userId: Long): UniversityUser


    // Season 관련 ---------------------------------------------------------------------------------
    // 나의 시즌 랭킹 정보 조회
    @GET("/season/user/{userId}/rank")
    suspend fun getMySeasonRanking(@Path("userId") userId: Long): SeasonUser

    // 시즌 유저 랭킹 5명 조회
    @GET("/season/user/{userId}/rank/5")
    suspend fun getSeasonTop5UserInfo(@Path("userId") userId: Long): List<Map<Int, SeasonUser>>

    // 시즌 유저 랭킹 전체 조회
    @GET("/season/rank/all")
    suspend fun getAllSeasonRanking(@Query("page") page: Int): PagingSeasonUser


    // Planet User 관련 ----------------------------------------------------------------------------
    // 플래넷 유저 랭킹 상위 3명 조회
    @GET("/user/rank")
    suspend fun getTop3PlanetUser(): List<HigherPlanetUser>

    // 나의 시즌 랭킹 정보 조회
    @GET("/user/{userId}/rank")
    suspend fun getMyPlanetRanking(@Path("userId") userId: Long): PlanetRankingUser

    // 플래넷 유저 랭킹 전체 조회
    @GET("/user/rank/all")
    suspend fun getAllPlanetUserRanking(@Query("page") page: Int): PagingPlanetUser

    @GET("/tier")
    suspend fun getTierList(): List<Tier>

    // 유저 정보 조회
    @GET("/user/{userId}")
    suspend fun getUserInfo(@Path("userId") userId: Long): UserInfo

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

    // 게시판
    @GET("/user/{userId}/university")
    suspend fun getUniversityName(@Path("userId") userId: Long): UserUniversityInfo

    @GET("/post/hot")
    suspend fun getPopularPosted(): List<PopularPostedInfo>

    @GET("/post/{type}")
    suspend fun readAllPosted(@Path("type") type: String): List<Posted>

    @GET("/post/view/{type}")
    suspend fun readViewPosted(@Path("type") type: String): ViewPosted

    @GET("/post/hot/{type}")
    suspend fun readHotPosted(@Path("type") type: String): HotPosted

    @GET("/post/my/{userId}/{type}")
    suspend fun readAllMyPosted(
        @Path("userId") userId: Long,
        @Path("type") type: String
    ): List<MyPostedInfo>

    @GET("/post/my/comment/{userId}/{type}")
    suspend fun readAllMyComment(
        @Path("userId") userId: Long,
        @Path("type") type: String
    ): List<MyPostedInfo>

    // 게시글
    @POST("/post/{type}")
    suspend fun postPosting(@Body postInfo: PostingInfo, @Path("type") type: String): PostResponse

    @GET("/post")
    suspend fun getPosted(@Query("postId") postId: Long, @Query("userId") userId: Long): PostedInfo

    @HTTP(method = "DELETE", path = "/post", hasBody = true)
    suspend fun deletePosted(@Body postId: PostId): PostResponse

    @POST("/post-heart")
    suspend fun postBoardHeartSave(@Body postId: PostId): PostResponse

    @HTTP(method = "DELETE", path = "/post-heart", hasBody = true)
    suspend fun deletePostedHeartSave(@Body postId: PostId): PostResponse

    // 댓글
    @POST("/comment")
    suspend fun postComment(@Body comment: CommentRequest): CommentResponse

    @GET("/comment")
    suspend fun getComment(
        @Query("postId") postId: Long,
        @Query("userId") userId: Long
    ): List<CommentInfo>

    @HTTP(method = "DELETE", path = "/comment", hasBody = true)
    suspend fun deleteComment(@Body commentId: CommentId): CommentResponse

    @POST("/comment-heart")
    suspend fun postCommentHeart(@Body commentId: CommentId): CommentResponse

    @HTTP(method = "DELETE", path = "/comment-heart", hasBody = true)
    suspend fun deleteCommentHeart(@Body commentId: CommentId): CommentResponse

    // 쪽지
    @POST("/chat")
    suspend fun postChat(@Body chat: ChatSave): ChatResponse

    @GET("/chat/chat-room/user/{userId}")
    suspend fun getAllChatroom(@Path("userId") userId: Long): List<ChatroomInfo>

    @GET("/chat/chat-room/{chatRoomId}/user/{userId}")
    suspend fun getAllChat(
        @Path("chatRoomId") chatRoomId: Long,
        @Path("userId") userId: Long
    ): ChatInfoResponse

    @HTTP(method = "DELETE", path = "/chat/chat-room", hasBody = true)
    suspend fun deleteChatRoom(@Body chatRoomId: ChatRoomId): ChatResponse

    @GET("/search/user/rank")
    suspend fun searchPlanetRank(@Query("search") search: String): List<PlanetRankingUser>

    @GET("/search/university/rank")
    suspend fun searchUniversityRank(@Query("search") search: String): List<University>

    @GET("/search/university/rank/user/{userId}")
    suspend fun searchUniversityUserRank(
        @Path("userId") userId: Long,
        @Query("search") search: String,
    ): List<UniversityUser>

    @GET("/search/season/rank")
    suspend fun searchSeasonRank(
        @Query("search") search: String
    ): List<SeasonUser>

    @GET("/search/post/history/user/{userId}")
    suspend fun getRecentlySearch(
        @Path("userId") userId: Long,
    ): List<String>

    @GET("/search/post/{type}/user/{userId}")
    suspend fun searchPosted(
        @Path("type") type: String,
        @Path("userId") userId: Long,
        @Query("search") search: String
    ): List<Posted>

    @GET("/search/chat/user/{userId}")
    suspend fun searchChat(
        @Path("userId") userId: Long,
        @Query("search") search: String
    ): List<ChatroomInfo>

    @GET("/search/chat/user/{userId}")
    suspend fun getRecentlyMapSearch(
        @Path("userId") userId: Long,
    ): List<SearchPlace>

    @GET("/search/map/user/{userId}")
    suspend fun searchPlace(
        @Path("userId") userId: Long,
        @Query("search") search: String
    ): SearchPlace

    @GET("/statistics/year/{year}/month/{month}/user/{userId}")
    suspend fun getMonthStatistics(
        @Path("year") year: Int,
        @Path("month") month: Int,
        @Path("userId") userId: Long,
    ): StatisticsPloggingInfo

    @GET("/statistics/week/user/{userId}")
    suspend fun getWeekStatistics(
        @Path("userId") userId: Long,
    ): StatisticsPloggingInfo

    @POST("/trash-can")
    suspend fun postTrashCan(@Body trashCanImage: TrashCanImage): TrashCanResponse
}



