package dev.limlei2.runners.run;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class RunRepository {

    private static final Logger log = LoggerFactory.getLogger(RunRepository.class);
    private final JdbcClient jdbcClient;

    public RunRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<Run> findAll(){
        return jdbcClient.sql("SELECT * FROM RUN")
                .query(Run.class)
                .list();
    }

    public Optional<Run> findById(Integer id){
        return jdbcClient.sql("SELECT * FROM RUN WHERE ID = :id")
                .param("id", id)
                .query(Run.class)
                .optional();
    }

    public void create(Run run){
        var updated = jdbcClient.sql("INSERT INTO RUN (ID, TITLE, STARTED_ON, COMPLETED_ON, MILES, LOCATION) VALUES (?,?,?,?,?,?)")
                .params(List.of(run.id(), run.title(), run.startedOn(), run.completedOn(), run.miles(), run.location().toString()))
                .update();

        Assert.state(updated == 1, "Run not created" + run.title());
    }

    public void update(Run run, Integer id){
        var updated = jdbcClient.sql("UPDATE RUN SET TITLE = ?, STARTED_ON = ?, COMPLETED_ON = ?, MILES = ?, LOCATION = ? WHERE ID = ?")
                .params(List.of(run.title(), run.startedOn(), run.completedOn(), run.miles(), run.location().toString(), id))
                .update();

        Assert.state(updated == 1, "Run not updated" + run.title());
    }

    public void delete(Integer id){
        var updated = jdbcClient.sql("DELETE FROM RUN WHERE ID = ?")
                .params(id)
                .update();

        Assert.state(updated == 1, "Run not deleted" + id);
    }

    public int count(){
        return jdbcClient.sql("SELECT * FROM RUN").query().listOfRows().size();
    }

    public void saveAll(List<Run> runs){
        runs.forEach(this::create);
    }

    public List<Run> findByLocation(String location){
        return jdbcClient.sql("SELECT * FROM RUN WHERE LOCATION = :location")
                .param("location", location)
                .query(Run.class)
                .list();
    }

//    private List<Run> runs = new ArrayList<>();
//
//    List<Run> findAll(){
//        return runs;
//    }
//
//    Optional<Run> findById(Integer id){
//        return runs.stream()
//                .filter(run -> run.id().equals(id))
//                .findFirst();
//    }
//
//    void create(Run run){
//        runs.add(run);
//    }
//
//    void update(Run run, Integer id){
//        Optional<Run> existingRun = findById(id);
//        if(existingRun.isPresent()){
//            runs.set(runs.indexOf(existingRun.get()), run);
//        }
//    }
//
//    void delete(Integer id){
//        runs.removeIf(run -> run.id().equals(id));
//    }
//
//    @PostConstruct
//    private void init(){
//        runs.add(new Run(1,
//                "First Run",
//                LocalDateTime.now(),
//                LocalDateTime.now().plusMinutes(30),
//                5,
//                Location.OUTDOOR));
//
//        runs.add(new Run(2,
//                "Second Run",
//                LocalDateTime.now(),
//                LocalDateTime.now().plusMinutes(60),
//                7,
//                Location.OUTDOOR));
//    }
}
