package net.edigest.journalApp.controller;

import net.edigest.journalApp.entity.User;
import net.edigest.journalApp.entity.journalEntry;
import net.edigest.journalApp.service.UserService;
import net.edigest.journalApp.service.journalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class journalEntryController {

    @Autowired
    private journalEntryService journalEntryService;
    @Autowired
    UserService userService;


    // GET all journal entries
    @GetMapping("/{userName}")
    public ResponseEntity<List<journalEntry>> getAllJouranalEntriesOfUser(@PathVariable String userName) {
       User user= userService.findByUsername(userName);
        List<journalEntry> all = user.getJournalEntries();
        if (all != null && !all.isEmpty()) {
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // GET journal entry by ID
    @GetMapping("id/{myid}")
    public ResponseEntity<journalEntry> getJournalEntryById(@PathVariable ObjectId myid) {
        Optional<journalEntry> entry = journalEntryService.findById(myid);
        return entry.map(journalEntry -> new ResponseEntity<>(journalEntry, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // POST create new journal entry
    @PostMapping("/{userName}")
    public ResponseEntity<journalEntry> createEntry(@RequestBody journalEntry myEntry, @PathVariable String userName ) {
        try {
            journalEntryService.saveEntry(myEntry, userName);
            return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // DELETE journal entry by ID
    @DeleteMapping("id/{userName}/{myid}")
    public ResponseEntity<Void> deleteJournalEntryById(@PathVariable ObjectId myid,@PathVariable String userName) {
        journalEntryService.deleteById(myid,userName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // PUT update journal entry by ID
   /*  @PutMapping("id/{id}")
    public ResponseEntity<journalEntry> updateJournalEntryById(
            @PathVariable ObjectId id,
            @RequestBody journalEntry newEntry) {

       Optional<journalEntry> optionalOldEntry = journalEntryService.findById(id);

        if (optionalOldEntry.isPresent()) {
            journalEntry oldEntry = optionalOldEntry.get();
            oldEntry.setTitle((newEntry.getTitle() != null && !newEntry.getTitle().trim().isEmpty()) ? newEntry.getTitle() : oldEntry.getTitle());
            oldEntry.setContent((newEntry.getContent() != null && !newEntry.getContent().trim().isEmpty()) ? newEntry.getContent() : oldEntry.getContent());
            journalEntryService.saveEntry(oldEntry, user);
            return new ResponseEntity<>(oldEntry, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);


    }

    */
}
