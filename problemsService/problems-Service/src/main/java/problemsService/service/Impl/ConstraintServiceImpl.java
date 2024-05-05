package problemsService.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import problemsService.Repository.ConstrainsRepository;
import problemsService.entities.Constrains;
import problemsService.service.ConstraintService;

@Service
public class ConstraintServiceImpl implements ConstraintService {
    @Autowired
    private ConstrainsRepository constrainsRepository;
    @Override
    public void addConstrains(Constrains constrains) {
        constrainsRepository.save(constrains);
    }
}
