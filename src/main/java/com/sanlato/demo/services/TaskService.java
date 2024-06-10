package com.sanlato.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanlato.demo.models.Task;
import com.sanlato.demo.models.user;
import com.sanlato.demo.repositories.TaskRepository;
import com.sanlato.demo.services.exceptions.DataBindingViolationException;
import com.sanlato.demo.services.exceptions.ObjectNotFoundException;

import jakarta.transaction.Transactional;

@Service
public class TaskService {

@Autowired
private TaskRepository taskRepository;

@Autowired
private UserService userService;

public Task findById(long id){
    Optional<Task> task = this.taskRepository.findById(id);
    return task.orElseThrow(() -> new ObjectNotFoundException(
        "Tarefa nao encontrada! id:" + id + ", Tipo: " + Task.class.getName()));
   
}

public List<Task> findAllByUserId(Long userid){
    List<Task> tasks = this.taskRepository.findByUser_Id(userid);
    return tasks;
} 


@Transactional
public Task create (Task obj){
    user user = this.userService.findById(obj.getUser().getId());
    obj.setId(null);
    obj.setUser(user);
    obj = this.taskRepository.save(obj);
    return obj;
}

@Transactional
public Task update (Task obj){
    Task newObj = findById(obj.getId());
    newObj.setDescription(obj.getDescription());
    return this.taskRepository.save(newObj);
}

public void delete(long id) {
    findById(id);
    try {
        this.taskRepository.deleteById(id);
    } catch (Exception e) {
        throw new DataBindingViolationException( "Nao e possivel excluir pois ha entidades relacionadas");
    }
}

}