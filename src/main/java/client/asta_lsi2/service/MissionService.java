package client.asta_lsi2.service;

import client.asta_lsi2.models.Apprenti;
import client.asta_lsi2.models.Mission;
import client.asta_lsi2.repository.MissionRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MissionService {
    private final MissionRepository missionRepository;
    public MissionService(MissionRepository missionRepository) {
        this.missionRepository = missionRepository;
    }

    public List<Apprenti> findApprentisByMotCleContainingIgnoreCase(String motCle) {
        List<Mission> missions= missionRepository.findMissionsByMotCleContainingIgnoreCase(motCle);
        return missions.stream()
                .map(Mission::getApprenti)
                .distinct()
                .toList();
    }
}
