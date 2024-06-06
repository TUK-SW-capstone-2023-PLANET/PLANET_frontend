package com.example.planet.presentation.viewmodel

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.planet.TAG
import com.example.planet.data.remote.dto.ApiState
import com.example.planet.data.remote.dto.ImageUrl
import com.example.planet.data.remote.dto.request.post.CommentId
import com.example.planet.data.remote.dto.request.post.CommentRequest
import com.example.planet.data.remote.dto.request.post.PostId
import com.example.planet.data.remote.dto.request.post.PostingInfo
import com.example.planet.data.remote.dto.response.post.CommentInfo
import com.example.planet.data.remote.dto.response.post.CommentResponse
import com.example.planet.data.remote.dto.response.post.HotPosted
import com.example.planet.data.remote.dto.response.post.PopularPostedInfo
import com.example.planet.data.remote.dto.response.post.PostResponse
import com.example.planet.data.remote.dto.response.post.Posted
import com.example.planet.data.remote.dto.response.post.PostedInfo
import com.example.planet.data.remote.dto.response.post.ViewPosted
import com.example.planet.data.remote.dto.response.user.UserUniversityInfo
import com.example.planet.domain.usecase.board.GetAllPostedUseCase
import com.example.planet.domain.usecase.board.GetHotPostedUseCase
import com.example.planet.domain.usecase.board.GetPopularPostedListUseCase
import com.example.planet.domain.usecase.board.GetViewPostedUseCase
import com.example.planet.domain.usecase.image.PostImagesUseCase
import com.example.planet.domain.usecase.login.sharedpreference.GetUserTokenUseCase
import com.example.planet.domain.usecase.post.DeleteCommentHeartUseCase
import com.example.planet.domain.usecase.post.DeleteCommentUseCase
import com.example.planet.domain.usecase.post.DeletePostedHeartSaveUseCase
import com.example.planet.domain.usecase.post.DeletePostedUseCase
import com.example.planet.domain.usecase.post.GetCommentListReadUseCase
import com.example.planet.domain.usecase.post.GetPostedInfoUseCase
import com.example.planet.domain.usecase.post.PostCommentHeartUseCase
import com.example.planet.domain.usecase.post.PostCommentSaveUseCase
import com.example.planet.domain.usecase.post.PostPostedHeartSaveUseCase
import com.example.planet.domain.usecase.post.PostPostingSaveUseCase
import com.example.planet.domain.usecase.search.GetRecentlySearchUseCase
import com.example.planet.domain.usecase.user.GetMyUniversityUseCase
import com.example.planet.presentation.util.asMultipart
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommunityViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val getUserTokenUseCase: GetUserTokenUseCase,
    private val postPostingSaveUseCase: PostPostingSaveUseCase,
    private val getPostedInfoUseCase: GetPostedInfoUseCase,
    private val deletePostedUseCase: DeletePostedUseCase,
    private val postPostedHeartSaveUseCase: PostPostedHeartSaveUseCase,
    private val deletePostedHeartSaveUseCase: DeletePostedHeartSaveUseCase,
    private val postCommentSaveUseCase: PostCommentSaveUseCase,
    private val getCommentListReadUseCase: GetCommentListReadUseCase,
    private val deleteCommentUseCase: DeleteCommentUseCase,
    private val getAllPostedUseCase: GetAllPostedUseCase,
    private val getViewPostedUseCase: GetViewPostedUseCase,
    private val getHotPostedUseCase: GetHotPostedUseCase,
    private val getMyUniversityUseCase: GetMyUniversityUseCase,
    private val getPopularPostedListUseCase: GetPopularPostedListUseCase,
    private val postCommentHeartUseCase: PostCommentHeartUseCase,
    private val deleteCommentHeartUseCase: DeleteCommentHeartUseCase,
    private val postImagesUseCase: PostImagesUseCase,
    private val getRecentlySearchUseCase: GetRecentlySearchUseCase
) : ViewModel() {

    var userId: Long = 0L
    var currentPostId: Long = 0
    var universityName = ""

    var postedDialogState by mutableStateOf(false)
    var boardDialogState by mutableStateOf(false)

    var postingTitleInput by mutableStateOf("")
    var postingContentInput by mutableStateOf("")
    var postingCommentInput by mutableStateOf("")

    var postingImageList by mutableStateOf(emptyList<Uri>())
    var imageList by mutableStateOf(emptyList<String>())

    var postedInfo by mutableStateOf(PostedInfo())

    var commentList by mutableStateOf(emptyList<CommentInfo>())

    var postedList by mutableStateOf(emptyList<Posted>())
    var viewPosted by mutableStateOf<ViewPosted?>(null)
    var hotPosted by mutableStateOf<HotPosted?>(null)

    private val _universityInfo = MutableStateFlow<UserUniversityInfo?>(null)
    val universityInfo: StateFlow<UserUniversityInfo?> = _universityInfo.asStateFlow()

    var popularPosted by mutableStateOf(emptyList<PopularPostedInfo>())

    var searchInput by mutableStateOf("")
//    var recentlySearch by mutableStateOf(emptyList<String>())
    var recentlySearch by mutableStateOf(emptyList<String>())
    var searchResult by mutableStateOf(emptyList<Posted>())


    init {
        viewModelScope.launch {
            getUserToken()
        }
    }


    private suspend fun getImageUrls(context: Context) {
        postingImageList.map { uri -> uri.asMultipart("files", context.contentResolver)!! }?.let {
            postImagesUseCase(it)
        }?.let { api ->
            when (val apiState = api.first()) {
                is ApiState.Success<*> -> {
                    imageList = (apiState.value as List<ImageUrl>).map { it.imageUrl }
                }

                is ApiState.Error -> {
                    Log.d(TAG, "getImageUrls Error: ${apiState.errMsg}")
                }

                ApiState.Loading -> TODO()
            }
        }
    }

    private suspend fun getUserToken(userTokenKey: String = "userToken") {
        when (val result = getUserTokenUseCase(userTokenKey).first()) {
            is ApiState.Success<*> -> {
                userId = result.value as Long
            }

            is ApiState.Error -> {
                Log.d(TAG, "getUserToken() 실패: ${result.errMsg}")
            }

            ApiState.Loading -> TODO()
        }
    }

    suspend fun getUniversityName() {
        when (val apiState = getMyUniversityUseCase(userId).first()) {
            is ApiState.Success<*> -> {
                _universityInfo.emit((apiState.value as UserUniversityInfo))
            }

            is ApiState.Error -> {
                Log.d("daeYoung", "getUniversityName() 실패: ${apiState.errMsg}")
            }

            ApiState.Loading -> TODO()
        }
    }

    suspend fun readPopularPostedList() {
        when (val apiState = getPopularPostedListUseCase().first()) {
            is ApiState.Success<*> -> {
                popularPosted = apiState.value as List<PopularPostedInfo>
            }

            is ApiState.Error -> {
                Log.d("daeYoung", "deleteComment() 실패: ${apiState.errMsg}")
            }

            ApiState.Loading -> TODO()
        }
    }

    suspend fun savePosting(type: String, onBack: () -> Unit) {
        getImageUrls(context)
        val postingInfo = PostingInfo(
            userId = userId,
            imageUrl = imageList,
            title = postingTitleInput,
            content = postingContentInput
        )

        when (val apiState =
            postPostingSaveUseCase(postingInfo = postingInfo, type = type).first()) {
            is ApiState.Success<*> -> {
                apiState.value as PostResponse
                Toast.makeText(context, "게시물 저장", Toast.LENGTH_SHORT).show()
                onBack()
            }

            is ApiState.Error -> {
                Log.d("daeYoung", "savePosting() 실패: ${apiState.errMsg}")
            }

            ApiState.Loading -> TODO()
        }
    }

    suspend fun readPostedInfo(postId: Long) {
        when (val apiState = getPostedInfoUseCase(postId = postId, userId = userId).first()) {
            is ApiState.Success<*> -> {
                postedInfo = apiState.value as PostedInfo
            }

            is ApiState.Error -> {
                Log.d("daeYoung", "readPostedInfo() 실패: ${apiState.errMsg}")
            }

            ApiState.Loading -> TODO()
        }
    }

    suspend fun deletePosted(postId: Long, onBack: () -> Unit) {
        val postId = PostId(postId = postId, userId = userId)

        when (val apiState = deletePostedUseCase(postId).first()) {
            is ApiState.Success<*> -> {
                if ((apiState.value as PostResponse).message == "게시물 삭제 성공") {
                    Toast.makeText(context, "게시글이 삭제되었습니다.", Toast.LENGTH_SHORT).show()
                    onBack()
                }
            }

            is ApiState.Error -> {
                Log.d("daeYoung", "readPostedInfo() 실패: ${apiState.errMsg}")
            }

            ApiState.Loading -> TODO()
        }
    }


    suspend fun savePostedHeart(postId: Long) {
        val postId = PostId(userId, postId)

        when (val apiState = postPostedHeartSaveUseCase(postId).first()) {
            is ApiState.Success<*> -> {
                if ((apiState.value as PostResponse).message == "게시물 좋아요 저장 성공") {
                    postedInfo =
                        postedInfo.copy(heart = true, heartCount = postedInfo.heartCount + 1)
                }
            }

            is ApiState.Error -> {
                Log.d("daeYoung", "savePostedHeart() 실패: ${apiState.errMsg}")
            }

            ApiState.Loading -> TODO()
        }
    }

    suspend fun deletePostedHeart(postId: Long) {
        val postId = PostId(userId, postId)

        when (val apiState = deletePostedHeartSaveUseCase(postId).first()) {
            is ApiState.Success<*> -> {
                if ((apiState.value as PostResponse).message == "게시물 좋아요 삭제 성공") {
                    postedInfo =
                        postedInfo.copy(heart = false, heartCount = postedInfo.heartCount - 1)
                }
            }

            is ApiState.Error -> {
                Log.d("daeYoung", "deletePostedHeart() 실패: ${apiState.errMsg}")
            }

            ApiState.Loading -> TODO()
        }
    }

    suspend fun saveComment(postId: Long, keyboardOnHide: () -> Unit) {
        val comment = CommentRequest(
            userId = userId,
            postId = postId,
            content = postingCommentInput
        )
        when (val apiState = postCommentSaveUseCase(comment).first()) {
            is ApiState.Success<*> -> {
                postingCommentInput = ""
                readCommentList(postedInfo.postId)
                keyboardOnHide()
            }

            is ApiState.Error -> {
                Log.d("daeYoung", "saveComment() 실패: ${apiState.errMsg}")
            }

            ApiState.Loading -> TODO()
        }
    }

    suspend fun readCommentList(postId: Long) {
        when (val apiState = getCommentListReadUseCase(postId = postId, userId = userId).first()) {
            is ApiState.Success<*> -> {
                commentList = (apiState.value as List<CommentInfo>)
//                (apiState.value as List<CommentInfo>).forEach { comment ->
//                    _commentList.add(comment)
//                }
                Log.d("daeYoung", "_commentList: ${commentList}")
            }

            is ApiState.Error -> {
                Log.d("daeYoung", "readCommentList() 실패: ${apiState.errMsg}")
            }

            ApiState.Loading -> TODO()
        }
    }

    suspend fun deleteComment(commentId: Long, menuOnHide: () -> Unit) {
        val commentId = CommentId(commentId, userId)

        when (val apiState = deleteCommentUseCase(commentId).first()) {
            is ApiState.Success<*> -> {
                if ((apiState.value as CommentResponse).message == "댓글 삭제 성공") {
                    readCommentList(postedInfo.postId)
                    menuOnHide()
                }
            }

            is ApiState.Error -> {
                Log.d("daeYoung", "deleteComment() 실패: ${apiState.errMsg}")
            }

            ApiState.Loading -> TODO()
        }
    }

    suspend fun saveCommentHeart(commentId: Long) {
        val commentId = CommentId(commentId, userId)
        when (val apiState = postCommentHeartUseCase(commentId).first()) {
            is ApiState.Success<*> -> {
                val result = (apiState.value as CommentResponse)
                if (result.message == "댓글 좋아요 저장 성공") {
                    commentList = commentList.map { comment ->
                        if (comment.commentId == result.commentId) {
                            comment.copy(
                                heartCount = comment.heartCount + 1,
                                heart = !comment.heart
                            )
                        } else comment
                    }
                }

            }

            is ApiState.Error -> {
                Log.d("daeYoung", "saveCommentHeart() 실패: ${apiState.errMsg}")
            }

            ApiState.Loading -> TODO()
        }
    }

    suspend fun deleteCommentHeart(commentId: Long) {
        val commentId = CommentId(commentId, userId)
        when (val apiState = deleteCommentHeartUseCase(commentId).first()) {
            is ApiState.Success<*> -> {
                val result = (apiState.value as CommentResponse)
                if (result.message == "댓글 좋아요 삭제 성공") {
                    commentList = commentList.map { comment ->
                        if (comment.commentId == result.commentId) {
                            comment.copy(
                                heartCount = comment.heartCount - 1,
                                heart = !comment.heart
                            )
                        } else comment
                    }
                }

            }

            is ApiState.Error -> {
                Log.d("daeYoung", "deleteCommentHeart() 실패: ${apiState.errMsg}")
            }

            ApiState.Loading -> TODO()
        }
    }

    suspend fun readAllPosted(type: String) {
        when (val apiState = getAllPostedUseCase(type).first()) {
            is ApiState.Success<*> -> {
                postedList = (apiState.value as List<Posted>)

            }

            is ApiState.Error -> {
                Log.d("daeYoung", "readAllPosted() 실패: ${apiState.errMsg}")
            }

            ApiState.Loading -> TODO()
        }
    }

    suspend fun readViewPosted(type: String) {
        when (val apiState = getViewPostedUseCase(type).first()) {
            is ApiState.Success<*> -> {
                viewPosted = (apiState.value as ViewPosted)
            }

            is ApiState.Error -> {
                Log.d("daeYoung", "readViewPosted() 실패: ${apiState.errMsg}")
            }

            ApiState.Loading -> TODO()
        }
    }

    suspend fun readHotPosted(type: String) {
        when (val apiState = getHotPostedUseCase(type).first()) {
            is ApiState.Success<*> -> {
                hotPosted = (apiState.value as HotPosted)
            }

            is ApiState.Error -> {
                Log.d("daeYoung", "readViewPosted() 실패: ${apiState.errMsg}")
            }

            ApiState.Loading -> TODO()
        }
    }

    suspend fun readRecentlySearch() {
        when (val apiState = getRecentlySearchUseCase(userId).first()) {
            is ApiState.Success<*> -> {
                recentlySearch = (apiState.value as List<String>)
            }

            is ApiState.Error -> {
                Log.d("daeYoung", "readViewPosted() 실패: ${apiState.errMsg}")
            }
            ApiState.Loading -> TODO()
        }
    }



}