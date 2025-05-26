package net.edigest.journalApp.service;

import net.edigest.journalApp.entity.User;
import net.edigest.journalApp.entity.journalEntry;
import net.edigest.journalApp.repository.journalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class journalEntryService {

    @Autowired
    private journalEntryRepository JournalEntryRepository;
    @Autowired
    private UserService userService;


    public void saveEntry(journalEntry Journalentry, String userName){
        User user= userService.findByUsername(userName);
        Journalentry.setDate(LocalDateTime.now());
        journalEntry saved = JournalEntryRepository.save(Journalentry);
        user.getJournalEntries().add(saved);
        userService.saveEntry(user);

    }
public List<journalEntry> getAll(){
        return JournalEntryRepository.findAll();
}
public Optional<journalEntry> findById (ObjectId id){
        return JournalEntryRepository.findById(id);
}
public void deleteById(ObjectId id, String userName)
{
    User user= userService.findByUsername(userName);
    user.getJournalEntries().removeIf(x-> x.getId().equals(id));
    userService.saveEntry(user);
    JournalEntryRepository.deleteById(id);
}

}
