package com.sts.service;

import com.google.gson.Gson;
import com.sts.domain.QVehicle;
import com.sts.domain.User;
import com.sts.domain.Vehicle;
import com.sts.domain.WorksIn;
import com.sts.repository.VehicleRepository;
import com.sts.repository.search.VehicleSearchRepository;
import com.sts.security.AuthoritiesConstants;
import com.sts.security.SecurityUtils;
import com.sts.service.dto.VehicleDTO;
import com.sts.service.mapper.VehicleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Vehicle}.
 */
@Service
@Transactional
public class VehicleService {

    private final Logger log = LoggerFactory.getLogger(VehicleService.class);

    private final VehicleRepository vehicleRepository;

    private final VehicleMapper vehicleMapper;

    private final VehicleSearchRepository vehicleSearchRepository;

    @Autowired
    UserService userService;

    @Autowired
    WorksInService worksInService;

    public VehicleService(VehicleRepository vehicleRepository, VehicleMapper vehicleMapper, VehicleSearchRepository vehicleSearchRepository) {
        this.vehicleRepository = vehicleRepository;
        this.vehicleMapper = vehicleMapper;
        this.vehicleSearchRepository = vehicleSearchRepository;
    }

    /**
     * Save a vehicle.
     *
     * @param vehicleDTO the entity to save.
     * @return the persisted entity.
     */
    public VehicleDTO save(VehicleDTO vehicleDTO) {
        log.debug("Request to save Vehicle : {}", vehicleDTO);
        Vehicle vehicle = vehicleMapper.toEntity(vehicleDTO);
        vehicle = vehicleRepository.save(vehicle);
        VehicleDTO result = vehicleMapper.toDto(vehicle);
        vehicleSearchRepository.save(vehicle);
        return result;
    }

    /**
     * Get all the vehicles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<VehicleDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Vehicles");

        QVehicle qVehicle=  QVehicle.vehicle;
        String login = SecurityUtils.getCurrentUserLogin().get();
        User user = userService.getUserWithAuthoritiesByLogin(login).get();

        if(SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)){
            return vehicleRepository.findAll(pageable)
                .map(vehicleMapper::toDto);
        }
       /* else  if(SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.MODERATOR))
        {
            WorksIn worksIn = worksInService.findByEmployeeId(user.getId());

            System.out.println(user.getLogin() + " " + user.getId());
            System.out.println(new Gson().toJson(worksIn));

            return vehicleRepository.findAll(qVehicle.company.id.eq(worksIn.getCompany().getId()), pageable)
                .map(vehicleMapper::toDto);


        }*/
        else  if(SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.MODERATOR))
        {
            WorksIn worksIn = worksInService.findByEmployeeIsCurrentUser();
            return vehicleRepository.findAll(qVehicle.company.id.eq(worksIn.getCompany().getId()), pageable)
                .map(vehicleMapper::toDto);


        }


        return vehicleRepository.findAll(pageable)
            .map(vehicleMapper::toDto);
    }

    /**
     * Get one vehicle by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<VehicleDTO> findOne(Long id) {
        log.debug("Request to get Vehicle : {}", id);
        return vehicleRepository.findById(id)
            .map(vehicleMapper::toDto);
    }

    /**
     * Delete the vehicle by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Vehicle : {}", id);
        vehicleRepository.deleteById(id);
        vehicleSearchRepository.deleteById(id);
    }

    /**
     * Search for the vehicle corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<VehicleDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Vehicles for query {}", query);
        return vehicleSearchRepository.search(queryStringQuery(query), pageable)
            .map(vehicleMapper::toDto);
    }
}
