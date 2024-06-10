package com.sanlato.demo.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sanlato.demo.models.user;
import com.sanlato.demo.repositories.UserRepository;
import com.sanlato.demo.services.exceptions.DataBindingViolationException;
import com.sanlato.demo.services.exceptions.ObjectNotFoundException;



@Service
public class UserService {
  
 @Autowired   
 private UserRepository userRepository;




 public user findById(Long id) {
    Optional<user> user =this.userRepository.findById(id);
    return user.orElseThrow(() -> new ObjectNotFoundException ("usuario nao encontrado! id " + id + ", Tipo" + user.class.getName() ));
}

 
 @Transactional
 public user create ( user obj) {
    obj.setId( null);
obj = this.userRepository.save(obj);
return obj;
 }


@Transactional
 public user update(user obj){
user newObj = findById(obj.getId());
newObj.setPassword(obj.getPassword());
return  this.userRepository.save(newObj);
}


 public void delete (Long id){
    findById(id);
    try{
        this.userRepository.deleteById(id);
    } catch(Exception e){
        throw new DataBindingViolationException("Nao e possivel excluir pois ha entidades relacionadas");
    }
 }
}