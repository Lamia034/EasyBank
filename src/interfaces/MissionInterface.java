package interfaces;

import dto.Mission;

import java.util.Optional;

public interface MissionInterface {
    Optional<Mission> addMission(Mission mission);
    Optional<Boolean> deleteMissionByCode(Integer missionCodeToDelete);
}