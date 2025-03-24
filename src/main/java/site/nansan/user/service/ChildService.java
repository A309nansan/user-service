package site.nansan.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.nansan.user.domain.Child;
import site.nansan.user.dto.ChildRequestDto;
import site.nansan.user.repository.ChildRepository;
import site.nansan.user.repository.UserChildRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChildService {

    private final ChildRepository childRepository;
    private final UserChildRepository userChildRepository;

    /**
     * 새로운 자녀 정보 생성
     * @param childRequestDto 자녀 정보 요청 객체
     * @return 생성된 자녀 객체
     */
    public Child createChild(ChildRequestDto childRequestDto) {
        Child child = new Child();
        child.setName(childRequestDto.getName());
        child.setProfileImageUrl(childRequestDto.getProfileImageUrl());
        child.setBirthDate(childRequestDto.getBirthDate());
        child.setGrade(childRequestDto.getGrade());
        child.setGender(childRequestDto.getGender());

        return childRepository.save(child);
    }
    /**
     * 사용자 ID로 연결된 자녀 정보 조회
     * @param userId 사용자 ID
     * @return 자녀 객체 또는 null
     */
    public Child getChildByUserId(Long userId) {
        // User_Child 테이블에서 해당 userId와 연관된 childId 찾기
        Long childId = userChildRepository.findChildIdByUserId(userId);

        if (childId == null) {
            return null;
        }

        return childRepository.findById(childId).orElse(null);
    }

    /**
     * 사용자의 모든 자녀 목록 조회
     * @param userId 사용자 ID
     * @return 자녀 목록
     */
    public List<Child> getChildListByUserId(Long userId) {
        List<Long> childIds = userChildRepository.findChildIdsByUserId(userId);
        return childRepository.findAllByIdIn(childIds);
    }
}