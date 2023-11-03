package com.app.usearth.service;

import com.app.usearth.domain.CommentDTO;
import com.app.usearth.domain.CommentVO;
import com.app.usearth.domain.FindPostCommentDTO;
import com.app.usearth.domain.PostDTO;
import com.app.usearth.mapper.LikeMapper;
import com.app.usearth.repository.RecyclingAgentDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class RecyclingAgentServiceImpl implements RecyclingAgentService {

    private final RecyclingAgentDAO recyclingAgentDAO;

    private final LikeMapper likeMapper;

    @Override
    public List<PostDTO> getByRecycling(Long id) {
        return recyclingAgentDAO.selectByRecycling(id);
    }

    @Override
    public Optional<PostDTO> getByRecyclingReadId(Long id) {

        return recyclingAgentDAO.findByRecyclingReadId(id);
    }
    @Override
    public List<PostDTO> getRelatedPostsById(Long id) {

        return recyclingAgentDAO.findRelatedPostsById(id);
    }


    //   postId를 매개변수로 받아 해당 ID의 게시글에 달린 댓글들을 가져옴
//   DAO의 selectCommentsByPostId 메서드를 호출하여 이 작업을 수행하여 그 결과를 반환
    @Override
    public List<CommentDTO> getCommentsByPostId(Long postId) {
        return recyclingAgentDAO.selectCommentsByPostId(postId);
    }

//  CommentDTO 객체를 매개변수로 받아 댓글 정보를 DB에 추가
//  즉, DAO의 insertComment 메서드를 호출하여 댓글을 DB에 삽입
    @Override
    public void addComment(CommentVO commentVO) {
        recyclingAgentDAO.insertComment(commentVO);
    }

    // 재활용 대행 신청
    @Override
    public void addPost(PostDTO postDTO) {

        recyclingAgentDAO.insertPost(postDTO);

    }

    @Override
    public void updatePost(PostDTO postDTO) {

        recyclingAgentDAO.updatePost(postDTO);
    }


    @Override
    public PostDTO getPostById(Long id) {

        return recyclingAgentDAO.getPostById(id);
    }

    // 게시글 조회수
    @Override
    public Long updateViewCount(Long id) {
        return recyclingAgentDAO.updateViewCount(id);
    }


    @Override
    public int getCommentCountByPostId(Long postId) {

        return recyclingAgentDAO.selectCommentCountByPostId(postId);
    }

    @Override
    public FindPostCommentDTO getCommentInfo(Long postId) {
        FindPostCommentDTO findPostCommentDTO = new FindPostCommentDTO();
        findPostCommentDTO.setPostComments(recyclingAgentDAO.findAllCommentByPostId(postId));
        findPostCommentDTO.setCommentCount(recyclingAgentDAO.findCommentCount(postId));
        return findPostCommentDTO;
    }

    // 좋아요 수 증감
    @Override
    public void toggleLike(Long userId, Long postId) {
        // 체크해서 해당 사용자가 이미 좋아요를 했는지 확인
        Long isLiked = likeMapper.checkUserLikeForPost(userId, postId);

        if (isLiked!=null) {
            log.info("{}",isLiked);
            likeMapper.removeLike(userId, postId);
        } else {
            likeMapper.addLike(userId, postId);
            log.info("{}",isLiked);
        }
    }


    @Override
    public int getLikeCountByPostId(Long postId) {
        return likeMapper.selectLikeCount(postId);
    }

    @Override
    public boolean userLiked(Long userId, Long postId) {
        return likeMapper.checkUserLikeForPost(userId, postId) > 0;
    }


}
