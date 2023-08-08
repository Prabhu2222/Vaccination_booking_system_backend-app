package com.example.VaccinationBookingSystem.service;

import com.example.VaccinationBookingSystem.DTO.RequestDto.DoctorRequestDto;
import com.example.VaccinationBookingSystem.DTO.ResponseDto.CenterResponseDto;
import com.example.VaccinationBookingSystem.DTO.ResponseDto.DoctorResponseDto;
import com.example.VaccinationBookingSystem.DTO.ResponseDto.GeneralDoctorResponseDto;
import com.example.VaccinationBookingSystem.customException.CenterNotFoundException;
import com.example.VaccinationBookingSystem.model.Doctor;
import com.example.VaccinationBookingSystem.model.VaccinationCenter;
import com.example.VaccinationBookingSystem.repository.DoctorRepository;
import com.example.VaccinationBookingSystem.repository.VaccinationCenterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DoctorService {
    @Autowired
    VaccinationCenterRepository centerRepository;
    @Autowired
    DoctorRepository doctorRepository;
    public DoctorResponseDto addDoctor(DoctorRequestDto doctorRequestDto) {
        //first check whether center exists or not
        Optional<VaccinationCenter> optionalCenter=centerRepository.findById(doctorRequestDto.getCenterId());
        if(optionalCenter.isEmpty()) throw new CenterNotFoundException("Sorry! Wrong center Id");

        VaccinationCenter center=optionalCenter.get();
        //add doctor ,convert dto ->dntity
        Doctor doctor=new Doctor();
        doctor.setName(doctorRequestDto.getName());
        doctor.setAge(doctorRequestDto.getAge());
        doctor.setEmailId(doctorRequestDto.getEmailId());
        doctor.setGender(doctorRequestDto.getGender());
        doctor.setCenter(center);//setting center

        //add in center's doctor list
        center.getDoctors().add(doctor);

        //saving to center repository
        VaccinationCenter savedCenter=centerRepository.save(center);//doctor will automatically be saved since doctor is the child of vaccination Center

        List<Doctor>doctors=savedCenter.getDoctors();
        Doctor latestSaveddoctor=doctors.get(doctors.size()-1);
        //prepare response Dto

        //prepare centerResponseDto
        CenterResponseDto centerResponseDto=new CenterResponseDto();
        centerResponseDto.setCenterType(savedCenter.getCenterType());
        centerResponseDto.setAddress(savedCenter.getAddress());
        centerResponseDto.setCenterName(savedCenter.getCenterName());


        DoctorResponseDto responseDto=new DoctorResponseDto();
        responseDto.setName(latestSaveddoctor.getName());
        responseDto.setMessage("Congrats!! you have been registered");
        responseDto.setCenterResponseDto(centerResponseDto);
        return responseDto;
    }

    public List<String> getByAgeGreaterThan(int age) {

        List<String> nameList=doctorRepository.getByAgeGreaterThanByColumn(age);
        return nameList;
    }

    public List<GeneralDoctorResponseDto> getDoctorsWithHighestAppointment() {
        List<Doctor> list=doctorRepository.findAll();
        int max=1;
        List<GeneralDoctorResponseDto> ansList=new ArrayList<>();
        for(Doctor ele:list){
            if(ele.getAppointments().size()>max){
                max=ele.getAppointments().size();
                ansList.clear();
                GeneralDoctorResponseDto obj=new GeneralDoctorResponseDto();
                obj.setName(ele.getName());
                obj.setAge(ele.getAge());
                obj.setEmailId(ele.getEmailId());
                obj.setGender(ele.getGender());
                obj.setNoOfAppointments(ele.getAppointments().size());
                ansList.add(obj);
            }else if(ele.getAppointments().size()==max){
                GeneralDoctorResponseDto obj=new GeneralDoctorResponseDto();
                obj.setName(ele.getName());
                obj.setAge(ele.getAge());
                obj.setEmailId(ele.getEmailId());
                obj.setGender(ele.getGender());
                obj.setNoOfAppointments(ele.getAppointments().size());
                ansList.add(obj);

            }
        }
        return ansList;
    }

    public List<GeneralDoctorResponseDto> getDoctorsAtCenter(int id) {
        List<VaccinationCenter>list=centerRepository.findAll();
        List<GeneralDoctorResponseDto>ansList=new ArrayList<>();
        for(VaccinationCenter ele:list){
            if(ele.getId()==id){
                List<Doctor>doctorList=ele.getDoctors();
                for(Doctor docEle:doctorList){
                    GeneralDoctorResponseDto obj=new GeneralDoctorResponseDto();
                    obj.setName(docEle.getName());
                    obj.setAge(docEle.getAge());
                    obj.setEmailId(docEle.getEmailId());
                    obj.setGender(docEle.getGender());
                    obj.setNoOfAppointments(docEle.getAppointments().size());
                    ansList.add(obj);
                }
            }
        }
        return ansList;
    }
}
