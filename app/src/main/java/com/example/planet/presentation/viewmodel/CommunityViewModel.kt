package com.example.planet.presentation.viewmodel

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.planet.TAG
import com.example.planet.data.remote.dto.ApiState
import com.example.planet.data.remote.dto.request.post.CommentId
import com.example.planet.data.remote.dto.request.post.CommentRequest
import com.example.planet.data.remote.dto.request.post.PostId
import com.example.planet.data.remote.dto.request.post.PostingInfo
import com.example.planet.data.remote.dto.response.post.CommentInfo
import com.example.planet.data.remote.dto.response.post.CommentResponse
import com.example.planet.data.remote.dto.response.post.PostResponse
import com.example.planet.data.remote.dto.response.post.PostedInfo
import com.example.planet.data.remote.dto.response.user.UserUniversityInfo
import com.example.planet.domain.usecase.login.sharedpreference.GetUserTokenUseCase
import com.example.planet.domain.usecase.post.DeleteCommentUseCase
import com.example.planet.domain.usecase.post.DeletePostedHeartSaveUseCase
import com.example.planet.domain.usecase.post.GetCommentListReadUseCase
import com.example.planet.domain.usecase.post.GetPostedInfoUseCase
import com.example.planet.domain.usecase.post.PostPostedHeartSaveUseCase
import com.example.planet.domain.usecase.post.PostCommentSaveUseCase
import com.example.planet.domain.usecase.post.PostPostingSaveUseCase
import com.example.planet.domain.usecase.user.GetMyUniversityUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommunityViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val getUserTokenUseCase: GetUserTokenUseCase,
    private val postPostingSaveUseCase: PostPostingSaveUseCase,
    private val getPostedInfoUseCase: GetPostedInfoUseCase,
    private val postPostedHeartSaveUseCase: PostPostedHeartSaveUseCase,
    private val deletePostedHeartSaveUseCase: DeletePostedHeartSaveUseCase,
    private val postCommentSaveUseCase: PostCommentSaveUseCase,
    private val getCommentListReadUseCase: GetCommentListReadUseCase,
    private val deleteCommentUseCase: DeleteCommentUseCase,
) : ViewModel() {

    var userId: Long = 0L
    var testPostId: Long = 1720021919

    var postedDialogState by mutableStateOf(false)
    var boardDialogState by mutableStateOf(false)

    var postingTitleInput by mutableStateOf("")
    var postingContentInput by mutableStateOf("")
    var postingCommentInput by mutableStateOf("")

    var postingImageList by mutableStateOf(emptyList<Uri>())

    var postedInfo by mutableStateOf(PostedInfo())

    var commentList = mutableStateOf(emptyList<CommentInfo>())

    init {
        viewModelScope.launch {
            getUserToken()
        }
    }


    //    suspend fun getImageUrl(uri: Uri, context: Context) {
//        uri.asMultipart("file", context.contentResolver)?.let { multipartBody ->
//            postImageUseCase(multipartBody)
//        }?.let { multipart ->
//            when (val apiState = multipart.first()) {
//                is ApiState.Success<*> -> {
//                    Log.d(TAG, "getImageUrl success: ${apiState.value as ImageUrl}")
//                    userInfo = userInfo.copy(imageUrl = (apiState.value).imageUrl)
//                }
//
//                is ApiState.Error -> {
//                    Log.d(TAG, "getImageUrl Error: ${apiState.errMsg}")
//                }
//
//                ApiState.Loading -> TODO()
//            }
//        }
//    }
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

    suspend fun getMyUniversity() {

    }

    suspend fun readPopularBoard() {

    }

    suspend fun savePosting(onBack: () -> Unit) {
        val postingInfo = PostingInfo(
            userId = userId,
            // TODO: 승민이가 멀티파일 리스트 api 만들면 수정
            imageUrl = listOf(
                "https://tuk-planet.s3.ap-northeast-2.amazonaws.com/cat/%EB%A9%94~%EB%A0%81.jpg",
                "https://tuk-planet.s3.ap-northeast-2.amazonaws.com/cat/%EA%B3%A0%EC%96%91%EC%9D%B4%ED%95%9C%ED%85%8C+%EB%A7%9E%EC%95%84%EB%B3%B8%EC%A0%81+%EC%9E%88%EB%8B%88.png"
            ),
            title = postingTitleInput,
            content = postingContentInput
        )
        when (val apiState = postPostingSaveUseCase(postingInfo).first()) {
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

    suspend fun getPostedInfo(goPostedInfoScreen: () -> Unit) {
        when (val apiState = getPostedInfoUseCase(postId = 1720021919, userId = userId).first()) {
            is ApiState.Success<*> -> {
                postedInfo = apiState.value as PostedInfo
                goPostedInfoScreen()
            }
            is ApiState.Error -> {
                Log.d("daeYoung", "getPostedInfo() 실패: ${apiState.errMsg}")
            }
            ApiState.Loading -> TODO()
        }
    }

    suspend fun savePostedHeart(postId: PostId) {

        when (val apiState = postPostedHeartSaveUseCase(postId).first()) {
            is ApiState.Success<*> -> {
                postedInfo = postedInfo.copy(heart = true)
            }
            is ApiState.Error -> {
                Log.d("daeYoung", "savePostedHeart() 실패: ${apiState.errMsg}")
            }
            ApiState.Loading -> TODO()
        }
    }
    suspend fun deletePostedHeart(postId: PostId) {
        when (val apiState = deletePostedHeartSaveUseCase(postId).first()) {
            is ApiState.Success<*> -> {
                postedInfo = postedInfo.copy(heart = false)
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
                readCommentList()
                keyboardOnHide()
            }
            is ApiState.Error -> {
                Log.d("daeYoung", "deletePostedHeart() 실패: ${apiState.errMsg}")
            }
            ApiState.Loading -> TODO()
        }
    }

    suspend fun readCommentList() {
        when (val apiState = getCommentListReadUseCase(postId = testPostId , userId = userId).first()) {
            is ApiState.Success<*> -> {
                commentList.value = apiState.value as List<CommentInfo>
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
                if((apiState.value as CommentResponse).message == "댓글 삭제 성공") {
                    readCommentList()
                    menuOnHide()
                }
            }
            is ApiState.Error -> {
                Log.d("daeYoung", "deleteComment() 실패: ${apiState.errMsg}")
            }
            ApiState.Loading -> TODO()
        }
    }






}