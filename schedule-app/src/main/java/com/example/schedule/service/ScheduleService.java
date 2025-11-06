package com.example.schedule.service;

import com.example.schedule.dto.ScheduleRequestDto;
import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.entity.Schedule;
import com.example.schedule.repository.ScheduleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    public ScheduleResponseDto create(ScheduleRequestDto requestDto) {
        Schedule schedule = new Schedule();
        schedule.setTitle(requestDto.getTitle());
        schedule.setContents(requestDto.getContents());
        schedule.setWriter(requestDto.getWriter());
        schedule.setPassword(requestDto.getPassword());
        scheduleRepository.save(schedule);
        return new ScheduleResponseDto(schedule);
    }

    public List<ScheduleResponseDto> findAll() {
        return scheduleRepository.findAll()
                .stream()
                .map(ScheduleResponseDto::new)
                .toList();
    }

    public ResponseEntity<?> findOne(Long id) {
        return scheduleRepository.findById(id)
                .map(schedule -> ResponseEntity.ok(new ScheduleResponseDto(schedule)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ScheduleResponseDto("해당 일정이 없습니다.")));
    }

    public ResponseEntity<?> update(Long id, ScheduleRequestDto requestDto) {
        Optional<Schedule> optional = scheduleRepository.findById(id);
        if (optional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 일정이 없습니다.");
        }
        Schedule schedule = optional.get();
        if (!schedule.getPassword().equals(requestDto.getPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("비밀번호가 일치하지 않습니다.");
        }
        schedule.setTitle(requestDto.getTitle());
        schedule.setWriter(requestDto.getWriter());
        scheduleRepository.save(schedule);
        return ResponseEntity.ok(new ScheduleResponseDto(schedule));
    }

    public ResponseEntity<?> delete(Long id, ScheduleRequestDto requestDto) {
        Optional<Schedule> optional = scheduleRepository.findById(id);
        if (optional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 일정이 없습니다.");
        }
        Schedule schedule = optional.get();
        if (!schedule.getPassword().equals(requestDto.getPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("비밀번호가 일치하지 않습니다.");
        }
        scheduleRepository.delete(schedule);
        return ResponseEntity.ok("삭제 완료");
    }
}
