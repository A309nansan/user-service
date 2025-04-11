package site.nansan.child.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import site.nansan.child.domain.Child;
import site.nansan.child.domain.Grade;
import site.nansan.child.repository.ChildRepository;
import site.nansan.child.util.GradeUtil;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class GradePromotionService {

    private final ChildRepository childRepository;

    /**
     * 매년 3월 1일, 9월 1일 0시에 실행되는 자동 진급 스케줄러
     */
    @Transactional
    @Scheduled(cron = "0 0 0 1 3,9 *") // 매년 3월 1일, 9월 1일 자정 실행
    public void promoteGrades() {
        LocalDate today = LocalDate.now();
        List<Child> children = childRepository.findAll();

        for (Child child : children) {
            Grade currentGrade = child.getGrade();
            Grade nextGrade = GradeUtil.getNextGrade(currentGrade, child.getBirthDate(), today);

            if (nextGrade != null && nextGrade != currentGrade) {
                child.setGrade(nextGrade);
            }
        }

        childRepository.saveAll(children);
    }
}
