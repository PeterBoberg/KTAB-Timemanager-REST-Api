//package se.karingotrafiken.timemanager.rest.utils;
//
//import org.joda.time.DateTime;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.ApplicationArguments;
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Component;
//import se.karingotrafiken.timemanager.rest.appmodel.common.DayType;
//import se.karingotrafiken.timemanager.rest.appmodel.common.Role;
//import se.karingotrafiken.timemanager.rest.entitys.*;
//import se.karingotrafiken.timemanager.rest.repository.*;
//import se.karingotrafiken.timemanager.rest.service.schedules.ScheduleService;
//import se.karingotrafiken.timemanager.rest.service.users.UserServiceImpl;
//
//import javax.ws.rs.client.ClientBuilder;
//import javax.ws.rs.core.GenericType;
//import javax.ws.rs.core.MediaType;
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Date;
//import java.util.List;
//
//@Component
//public class DataLoader implements ApplicationRunner {
//
//    @Autowired
//    private BoatRepository boatRepository;
//
//    @Autowired
//    private EmployeeRepository employeeRepository;
//
//    @Autowired
//    private ServiceCategoryRepository serviceCategoryRepository;
//
//    @Autowired
//    private ShiftDayPrototypeRepository shiftDayPrototypeRepository;
//
//    @Autowired
//    private ShiftPeriodRepository shiftPeriodRepository;
//
//    @Autowired
//    private ShiftRepository shiftRepository;
//
//    @Autowired
//    private UnionContractRepository unionContractRepository;
//
//    @Autowired
//    private UnionContractRuleRepository unionContractRuleRepository;
//
//    @Autowired
//    private WorkDayIndexRepository workDayIndexRepository;
//
//    @Autowired
//    private AccumulatedObHoursRepository accumulatedObHoursRepository;
//
//    @Autowired
//    private ScheduleService scheduleService;
//
//    @Autowired
//    private ScheduledDayRepository scheduledDayRepository;
//
//    @Autowired
//    private HolidayRepository holidayRepository;
//
//    @Autowired
//    private UserServiceImpl userService;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private WithdrawalRepository withdrawalRepository;
//
//    @Autowired
//    private BCryptPasswordEncoder passwordEncoder;
//
//    @Autowired
//    private BreakDateRepository breakDateRepository;
//
//
//    @Override
//    public void run(ApplicationArguments applicationArguments) throws Exception {
//
//        employeeRepository.deleteAll();
//        userRepository.deleteAll();
//
////        BreakDate breakDate = new BreakDate();
////        breakDate.setBreakDateType(BreakDateType.RESET_MONTHLY_WORKHOURS_DATE);
////        breakDate.setMonth(4);
////        breakDate.setDay(1);
////        breakDateRepository.save(breakDate);
//
//        Boat stromstierna = new Boat();
//        stromstierna.setName("Strömstierna");
//
//        Boat stromcrona = new Boat();
//        stromcrona.setName("Strömcrona");
//
//        boatRepository.save(stromstierna);
//        boatRepository.save(stromcrona);
//
//        // Creates two service categories
//        ServiceCategory commanderCategory = new ServiceCategory();
//        commanderCategory.setName("Befälhavare");
//
//        ServiceCategory decksmenCategory = new ServiceCategory();
//        decksmenCategory.setName("Däcksman");
//
//        serviceCategoryRepository.save(commanderCategory);
//        serviceCategoryRepository.save(decksmenCategory);
//
//        // Creates three employees
//        Employee employee1 = new Employee();
//        employee1.setFirstName("Anders");
//        employee1.setLastName("Boberg");
//        employee1.setCity("Kungälv");
//        employee1.setPostalCode("44235");
//        employee1.setStreetAddress("Galeasgatan 17");
//        employee1.setPhoneNumber("0706-386896");
//        employee1.setPersonalNumber("471105-1617");
//        employee1.setEmail("anders.boberg@anerbo.se");
//        employee1.setServiceCategory(commanderCategory);
//
//        User user1 = new User();
//        user1.setUsername("admin");
//        user1.setPassword(passwordEncoder.encode("admin"));
//        user1.setEnabled(true);
//        user1.setRoles(Arrays.asList(Role.ROLE_ADMIN, Role.ROLE_USER));
//        user1.setEmployee(employee1);
//        employee1.setUser(user1);
//
//        Employee employee2 = new Employee();
//        employee2.setFirstName("Kalle");
//        employee2.setLastName("Svensson");
//        employee2.setCity("Svanesund");
//        employee2.setPostalCode("33928");
//        employee2.setStreetAddress("Orustgatan 70");
//        employee2.setPhoneNumber("0776-449382");
//        employee2.setPersonalNumber("750917-3399");
//        employee2.setEmail("kalle@svensson.se");
//        employee2.setServiceCategory(commanderCategory);
//
//        User user2 = new User();
//        user2.setUsername("kallekalle");
//        user2.setPassword(passwordEncoder.encode("Qwerty12"));
//        user2.setEnabled(true);
//        user2.setRoles(Arrays.asList(Role.ROLE_USER, Role.ROLE_BOAT_MANAGER));
//        user2.setEmployee(employee2);
//        employee2.setUser(user2);
//
//        Employee employee3 = new Employee();
//        employee3.setFirstName("Olle");
//        employee3.setLastName("Bertssson");
//        employee3.setCity("Göteborg");
//        employee3.setPostalCode("33726");
//        employee3.setStreetAddress("Avenyn 35");
//        employee3.setPhoneNumber("0773-339922");
//        employee3.setPersonalNumber("781017-2223");
//        employee3.setEmail("olle@bertsson.se");
//        employee3.setServiceCategory(decksmenCategory);
//
//        User user3 = new User();
//        user3.setUsername("olleolle");
//        user3.setPassword(passwordEncoder.encode("Qwerty12"));
//        user3.setEnabled(true);
//        user3.setRoles(Arrays.asList(Role.ROLE_USER));
//        user3.setEmployee(employee3);
//        employee3.setUser(user3);
//
//        employeeRepository.save(employee1);
//        employeeRepository.save(employee2);
//        employeeRepository.save(employee3);
//
//
//        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
//        DateFormat timeFormatter = new SimpleDateFormat("HH:mm");
//
//        // Crates shift no 1
//        Shift shift1 = new Shift();
//        shift1.setLengthInDays(14);
//        shift1.setName("Sommarskift Strömstierna 2017");
//        shift1.setBoat(stromstierna);
//
//        shift1 = shiftRepository.save(shift1);
//
//        // Creates the indexes for the working days of "shift1" created above
//        WorkDayIndex workDayIndex11 = new WorkDayIndex();
//        workDayIndex11.setDayIndex(1);
//        workDayIndex11.setShift(shift1);
//
//        WorkDayIndex workDayIndex12 = new WorkDayIndex();
//        workDayIndex12.setDayIndex(2);
//        workDayIndex12.setShift(shift1);
//
//        WorkDayIndex workDayIndex13 = new WorkDayIndex();
//        workDayIndex13.setDayIndex(3);
//        workDayIndex13.setShift(shift1);
//
//        WorkDayIndex workDayIndex14 = new WorkDayIndex();
//        workDayIndex14.setDayIndex(4);
//        workDayIndex14.setShift(shift1);
//
//        WorkDayIndex workDayIndex15 = new WorkDayIndex();
//        workDayIndex15.setDayIndex(5);
//        workDayIndex15.setShift(shift1);
//
//        WorkDayIndex workDayIndex16 = new WorkDayIndex();
//        workDayIndex16.setDayIndex(6);
//        workDayIndex16.setShift(shift1);
//
//        WorkDayIndex workDayIndex17 = new WorkDayIndex();
//        workDayIndex17.setDayIndex(7);
//        workDayIndex17.setShift(shift1);
//
//        workDayIndexRepository.save(workDayIndex11);
//        workDayIndexRepository.save(workDayIndex12);
//        workDayIndexRepository.save(workDayIndex13);
//        workDayIndexRepository.save(workDayIndex14);
//        workDayIndexRepository.save(workDayIndex15);
//        workDayIndexRepository.save(workDayIndex16);
//        workDayIndexRepository.save(workDayIndex17);
//
//
//        // Creates prototypes for the days of "shift1" all days of the week + a holiday prototype and jump off/on day prototypes
//        ShiftDayPrototype shiftDayPrototype1 = new ShiftDayPrototype();
//        shiftDayPrototype1.setShift(shift1);
//        shiftDayPrototype1.setStartTimeOfDay(timeFormatter.parse("07:00"));
//        shiftDayPrototype1.setEndTimeOfDay(timeFormatter.parse("19:00"));
//        shiftDayPrototype1.setNetHours(8);
//        shiftDayPrototype1.setDayType(DayType.MONDAY);
//
//        ShiftDayPrototype shiftDayPrototype2 = new ShiftDayPrototype();
//        shiftDayPrototype2.setShift(shift1);
//        shiftDayPrototype2.setStartTimeOfDay(timeFormatter.parse("07:00"));
//        shiftDayPrototype2.setEndTimeOfDay(timeFormatter.parse("19:00"));
//        shiftDayPrototype2.setNetHours(8);
//        shiftDayPrototype2.setDayType(DayType.TUESDAY);
//
//        ShiftDayPrototype shiftDayPrototype3 = new ShiftDayPrototype();
//        shiftDayPrototype3.setShift(shift1);
//        shiftDayPrototype3.setStartTimeOfDay(timeFormatter.parse("07:00"));
//        shiftDayPrototype3.setEndTimeOfDay(timeFormatter.parse("19:00"));
//        shiftDayPrototype3.setNetHours(8);
//        shiftDayPrototype3.setDayType(DayType.WEDNESDAY);
//
//        ShiftDayPrototype shiftDayPrototype4 = new ShiftDayPrototype();
//        shiftDayPrototype4.setShift(shift1);
//        shiftDayPrototype4.setStartTimeOfDay(timeFormatter.parse("07:00"));
//        shiftDayPrototype4.setEndTimeOfDay(timeFormatter.parse("19:00"));
//        shiftDayPrototype4.setNetHours(8);
//        shiftDayPrototype4.setDayType(DayType.THURSDAY);
//
//        ShiftDayPrototype shiftDayPrototype5 = new ShiftDayPrototype();
//        shiftDayPrototype5.setShift(shift1);
//        shiftDayPrototype5.setStartTimeOfDay(timeFormatter.parse("07:00"));
//        shiftDayPrototype5.setEndTimeOfDay(timeFormatter.parse("19:00"));
//        shiftDayPrototype5.setNetHours(8);
//        shiftDayPrototype5.setDayType(DayType.FRIDAY);
//
//        ShiftDayPrototype shiftDayPrototype6 = new ShiftDayPrototype();
//        shiftDayPrototype6.setShift(shift1);
//        shiftDayPrototype6.setStartTimeOfDay(timeFormatter.parse("08:00"));
//        shiftDayPrototype6.setEndTimeOfDay(timeFormatter.parse("17:00"));
//        shiftDayPrototype6.setNetHours(8);
//        shiftDayPrototype6.setDayType(DayType.SATURDAY);
//
//        ShiftDayPrototype shiftDayPrototype7 = new ShiftDayPrototype();
//        shiftDayPrototype7.setShift(shift1);
//        shiftDayPrototype7.setStartTimeOfDay(timeFormatter.parse("08:00"));
//        shiftDayPrototype7.setEndTimeOfDay(timeFormatter.parse("16:30"));
//        shiftDayPrototype7.setNetHours(8);
//        shiftDayPrototype7.setDayType(DayType.SUNDAY);
//
//        ShiftDayPrototype shiftDayPrototype8 = new ShiftDayPrototype();
//        shiftDayPrototype8.setShift(shift1);
//        shiftDayPrototype8.setStartTimeOfDay(timeFormatter.parse("08:00"));
//        shiftDayPrototype8.setEndTimeOfDay(timeFormatter.parse("16:30"));
//        shiftDayPrototype8.setNetHours(8);
//        shiftDayPrototype8.setDayType(DayType.HOLIDAY);
//
//        ShiftDayPrototype shiftDayPrototype9 = new ShiftDayPrototype();
//        shiftDayPrototype9.setShift(shift1);
//        shiftDayPrototype9.setStartTimeOfDay(timeFormatter.parse("08:00"));
//        shiftDayPrototype9.setEndTimeOfDay(timeFormatter.parse("12:00"));
//        shiftDayPrototype9.setNetHours(4);
//        shiftDayPrototype9.setDayType(DayType.START_DAY);
//
//        ShiftDayPrototype shiftDayPrototype10 = new ShiftDayPrototype();
//        shiftDayPrototype10.setShift(shift1);
//        shiftDayPrototype10.setStartTimeOfDay(timeFormatter.parse("12:00"));
//        shiftDayPrototype10.setEndTimeOfDay(timeFormatter.parse("16:00"));
//        shiftDayPrototype10.setNetHours(4);
//        shiftDayPrototype10.setDayType(DayType.END_DAY);
//
//        shiftDayPrototype1 = shiftDayPrototypeRepository.save(shiftDayPrototype1);
//        shiftDayPrototype2 = shiftDayPrototypeRepository.save(shiftDayPrototype2);
//        shiftDayPrototype3 = shiftDayPrototypeRepository.save(shiftDayPrototype3);
//        shiftDayPrototype4 = shiftDayPrototypeRepository.save(shiftDayPrototype4);
//        shiftDayPrototype5 = shiftDayPrototypeRepository.save(shiftDayPrototype5);
//        shiftDayPrototype6 = shiftDayPrototypeRepository.save(shiftDayPrototype6);
//        shiftDayPrototype7 = shiftDayPrototypeRepository.save(shiftDayPrototype7);
//        shiftDayPrototype8 = shiftDayPrototypeRepository.save(shiftDayPrototype8);
//        shiftDayPrototype9 = shiftDayPrototypeRepository.save(shiftDayPrototype9);
//        shiftDayPrototype10 = shiftDayPrototypeRepository.save(shiftDayPrototype10);
//
//
//        // Creates accumulated OB hours for each service category associated with each of the shift day prototypes
//        // defined above
//
//        AccumulatedObHours accumulatedObHours11 = new AccumulatedObHours();
//        accumulatedObHours11.setAccumulatedHours(1);
//        accumulatedObHours11.setServiceCategory(commanderCategory);
//        accumulatedObHours11.setShiftDayPrototype(shiftDayPrototype1);
//
//        AccumulatedObHours accumulatedObHours12 = new AccumulatedObHours();
//        accumulatedObHours12.setAccumulatedHours(0);
//        accumulatedObHours12.setServiceCategory(decksmenCategory);
//        accumulatedObHours12.setShiftDayPrototype(shiftDayPrototype1);
//
//
//        AccumulatedObHours accumulatedObHours21 = new AccumulatedObHours();
//        accumulatedObHours21.setAccumulatedHours(1);
//        accumulatedObHours21.setServiceCategory(commanderCategory);
//        accumulatedObHours21.setShiftDayPrototype(shiftDayPrototype2);
//
//        AccumulatedObHours accumulatedObHours22 = new AccumulatedObHours();
//        accumulatedObHours22.setAccumulatedHours(0);
//        accumulatedObHours22.setServiceCategory(decksmenCategory);
//        accumulatedObHours22.setShiftDayPrototype(shiftDayPrototype2);
//
//        AccumulatedObHours accumulatedObHours31 = new AccumulatedObHours();
//        accumulatedObHours31.setAccumulatedHours(1);
//        accumulatedObHours31.setServiceCategory(commanderCategory);
//        accumulatedObHours31.setShiftDayPrototype(shiftDayPrototype3);
//
//        AccumulatedObHours accumulatedObHours32 = new AccumulatedObHours();
//        accumulatedObHours32.setAccumulatedHours(0);
//        accumulatedObHours32.setServiceCategory(decksmenCategory);
//        accumulatedObHours32.setShiftDayPrototype(shiftDayPrototype3);
//
//        AccumulatedObHours accumulatedObHours41 = new AccumulatedObHours();
//        accumulatedObHours41.setAccumulatedHours(1);
//        accumulatedObHours41.setServiceCategory(commanderCategory);
//        accumulatedObHours41.setShiftDayPrototype(shiftDayPrototype4);
//
//        AccumulatedObHours accumulatedObHours42 = new AccumulatedObHours();
//        accumulatedObHours42.setAccumulatedHours(0);
//        accumulatedObHours42.setServiceCategory(decksmenCategory);
//        accumulatedObHours42.setShiftDayPrototype(shiftDayPrototype4);
//
//        AccumulatedObHours accumulatedObHours51 = new AccumulatedObHours();
//        accumulatedObHours51.setAccumulatedHours(1);
//        accumulatedObHours51.setServiceCategory(commanderCategory);
//        accumulatedObHours51.setShiftDayPrototype(shiftDayPrototype5);
//
//        AccumulatedObHours accumulatedObHours52 = new AccumulatedObHours();
//        accumulatedObHours52.setAccumulatedHours(0);
//        accumulatedObHours52.setServiceCategory(decksmenCategory);
//        accumulatedObHours52.setShiftDayPrototype(shiftDayPrototype5);
//
//        AccumulatedObHours accumulatedObHours61 = new AccumulatedObHours();
//        accumulatedObHours61.setAccumulatedHours(8);
//        accumulatedObHours61.setServiceCategory(commanderCategory);
//        accumulatedObHours61.setShiftDayPrototype(shiftDayPrototype6);
//
//        AccumulatedObHours accumulatedObHours62 = new AccumulatedObHours();
//        accumulatedObHours62.setAccumulatedHours(0);
//        accumulatedObHours62.setServiceCategory(decksmenCategory);
//        accumulatedObHours62.setShiftDayPrototype(shiftDayPrototype6);
//
//        AccumulatedObHours accumulatedObHours71 = new AccumulatedObHours();
//        accumulatedObHours71.setAccumulatedHours(8);
//        accumulatedObHours71.setServiceCategory(commanderCategory);
//        accumulatedObHours71.setShiftDayPrototype(shiftDayPrototype7);
//
//        AccumulatedObHours accumulatedObHours72 = new AccumulatedObHours();
//        accumulatedObHours72.setAccumulatedHours(0);
//        accumulatedObHours72.setServiceCategory(decksmenCategory);
//        accumulatedObHours72.setShiftDayPrototype(shiftDayPrototype7);
//
//        AccumulatedObHours accumulatedObHours81 = new AccumulatedObHours();
//        accumulatedObHours81.setAccumulatedHours(8);
//        accumulatedObHours81.setServiceCategory(commanderCategory);
//        accumulatedObHours81.setShiftDayPrototype(shiftDayPrototype8);
//
//        AccumulatedObHours accumulatedObHours82 = new AccumulatedObHours();
//        accumulatedObHours82.setAccumulatedHours(8);
//        accumulatedObHours82.setServiceCategory(decksmenCategory);
//        accumulatedObHours82.setShiftDayPrototype(shiftDayPrototype8);
//
//        AccumulatedObHours accumulatedObHours91 = new AccumulatedObHours();
//        accumulatedObHours91.setAccumulatedHours(1);
//        accumulatedObHours91.setServiceCategory(commanderCategory);
//        accumulatedObHours91.setShiftDayPrototype(shiftDayPrototype9);
//
//        AccumulatedObHours accumulatedObHours92 = new AccumulatedObHours();
//        accumulatedObHours92.setAccumulatedHours(0);
//        accumulatedObHours92.setServiceCategory(decksmenCategory);
//        accumulatedObHours92.setShiftDayPrototype(shiftDayPrototype9);
//
//        AccumulatedObHours accumulatedObHours101 = new AccumulatedObHours();
//        accumulatedObHours101.setAccumulatedHours(2);
//        accumulatedObHours101.setServiceCategory(commanderCategory);
//        accumulatedObHours101.setShiftDayPrototype(shiftDayPrototype10);
//
//        AccumulatedObHours accumulatedObHours102 = new AccumulatedObHours();
//        accumulatedObHours102.setAccumulatedHours(0);
//        accumulatedObHours102.setServiceCategory(decksmenCategory);
//        accumulatedObHours102.setShiftDayPrototype(shiftDayPrototype10);
//
//
//        accumulatedObHoursRepository.save(accumulatedObHours11);
//        accumulatedObHoursRepository.save(accumulatedObHours12);
//        accumulatedObHoursRepository.save(accumulatedObHours21);
//        accumulatedObHoursRepository.save(accumulatedObHours22);
//        accumulatedObHoursRepository.save(accumulatedObHours31);
//        accumulatedObHoursRepository.save(accumulatedObHours32);
//        accumulatedObHoursRepository.save(accumulatedObHours41);
//        accumulatedObHoursRepository.save(accumulatedObHours42);
//        accumulatedObHoursRepository.save(accumulatedObHours51);
//        accumulatedObHoursRepository.save(accumulatedObHours52);
//        accumulatedObHoursRepository.save(accumulatedObHours61);
//        accumulatedObHoursRepository.save(accumulatedObHours62);
//        accumulatedObHoursRepository.save(accumulatedObHours71);
//        accumulatedObHoursRepository.save(accumulatedObHours72);
//        accumulatedObHoursRepository.save(accumulatedObHours81);
//        accumulatedObHoursRepository.save(accumulatedObHours82);
//        accumulatedObHoursRepository.save(accumulatedObHours91);
//        accumulatedObHoursRepository.save(accumulatedObHours92);
//        accumulatedObHoursRepository.save(accumulatedObHours101);
//        accumulatedObHoursRepository.save(accumulatedObHours102);
//
////        // Create shift no 2
////        Shift shift2 = new Shift();
////        shift2.setName("Sommarskift Strömcrona 2017");
////        shift2.setBoat(stromcrona);
////        shift2.setLengthInDays(14);
////        shiftRepository.save(shift2);
////
////        WorkDayIndex workDayIndex21 = new WorkDayIndex();
////        workDayIndex21.setDayIndex(1);
////        workDayIndex21.setShift(shift2);
////
////        WorkDayIndex workDayIndex22 = new WorkDayIndex();
////        workDayIndex22.setDayIndex(2);
////        workDayIndex22.setShift(shift2);
////
////        WorkDayIndex workDayIndex23 = new WorkDayIndex();
////        workDayIndex23.setDayIndex(3);
////        workDayIndex23.setShift(shift2);
////
////        WorkDayIndex workDayIndex24 = new WorkDayIndex();
////        workDayIndex24.setDayIndex(4);
////        workDayIndex24.setShift(shift2);
////
//////        WorkDayIndex workDayIndex25 = new WorkDayIndex();
//////        workDayIndex25.setDayIndex(5);
//////        workDayIndex25.setShift(shift2);
//////
//////        WorkDayIndex workDayIndex26 = new WorkDayIndex();
//////        workDayIndex26.setDayIndex(6);
//////        workDayIndex26.setShift(shift2);
//////
//////        WorkDayIndex workDayIndex27 = new WorkDayIndex();
//////        workDayIndex27.setDayIndex(7);
//////        workDayIndex27.setShift(shift2);
////
////        workDayIndexRepository.save(workDayIndex21);
////        workDayIndexRepository.save(workDayIndex22);
////        workDayIndexRepository.save(workDayIndex23);
////        workDayIndexRepository.save(workDayIndex24);
//////        workDayIndexRepository.save(workDayIndex25);
//////        workDayIndexRepository.save(workDayIndex26);
//////        workDayIndexRepository.save(workDayIndex27);
////
////        ShiftDayPrototype shiftDayPrototype21 = new ShiftDayPrototype();
////        shiftDayPrototype21.setDayType(DayType.MONDAY);
////        shiftDayPrototype21.setNetHours(4);
////        shiftDayPrototype21.setStartTimeOfDay(timeFormatter.parse("10:00"));
////        shiftDayPrototype21.setEndTimeOfDay(timeFormatter.parse("14:00"));
////        shiftDayPrototype21.setShift(shift2);
////
////        ShiftDayPrototype shiftDayPrototype22 = new ShiftDayPrototype();
////        shiftDayPrototype22.setDayType(DayType.TUESDAY);
////        shiftDayPrototype22.setNetHours(4);
////        shiftDayPrototype22.setStartTimeOfDay(timeFormatter.parse("10:00"));
////        shiftDayPrototype22.setEndTimeOfDay(timeFormatter.parse("14:00"));
////        shiftDayPrototype22.setShift(shift2);
////
////        ShiftDayPrototype shiftDayPrototype23 = new ShiftDayPrototype();
////        shiftDayPrototype23.setDayType(DayType.WEDNESDAY);
////        shiftDayPrototype23.setNetHours(4);
////        shiftDayPrototype23.setStartTimeOfDay(timeFormatter.parse("10:00"));
////        shiftDayPrototype23.setEndTimeOfDay(timeFormatter.parse("14:00"));
////        shiftDayPrototype23.setShift(shift2);
////
////        ShiftDayPrototype shiftDayPrototype24 = new ShiftDayPrototype();
////        shiftDayPrototype24.setDayType(DayType.THURSDAY);
////        shiftDayPrototype24.setNetHours(4);
////        shiftDayPrototype24.setStartTimeOfDay(timeFormatter.parse("10:00"));
////        shiftDayPrototype24.setEndTimeOfDay(timeFormatter.parse("14:00"));
////        shiftDayPrototype24.setShift(shift2);
////
////        ShiftDayPrototype shiftDayPrototype25 = new ShiftDayPrototype();
////        shiftDayPrototype25.setDayType(DayType.FRIDAY);
////        shiftDayPrototype25.setNetHours(4);
////        shiftDayPrototype25.setStartTimeOfDay(timeFormatter.parse("10:00"));
////        shiftDayPrototype25.setEndTimeOfDay(timeFormatter.parse("14:00"));
////        shiftDayPrototype25.setShift(shift2);
////
////        ShiftDayPrototype shiftDayPrototype26 = new ShiftDayPrototype();
////        shiftDayPrototype26.setDayType(DayType.SATURDAY);
////        shiftDayPrototype26.setNetHours(3);
////        shiftDayPrototype26.setStartTimeOfDay(timeFormatter.parse("11:00"));
////        shiftDayPrototype26.setEndTimeOfDay(timeFormatter.parse("13:00"));
////        shiftDayPrototype26.setShift(shift2);
////
////        ShiftDayPrototype shiftDayPrototype27 = new ShiftDayPrototype();
////        shiftDayPrototype27.setDayType(DayType.SUNDAY);
////        shiftDayPrototype27.setNetHours(3);
////        shiftDayPrototype27.setStartTimeOfDay(timeFormatter.parse("11:00"));
////        shiftDayPrototype27.setEndTimeOfDay(timeFormatter.parse("13:00"));
////        shiftDayPrototype27.setShift(shift2);
////
////        ShiftDayPrototype shiftDayPrototype28 = new ShiftDayPrototype();
////        shiftDayPrototype28.setDayType(DayType.HOLIDAY);
////        shiftDayPrototype28.setNetHours(3);
////        shiftDayPrototype28.setStartTimeOfDay(timeFormatter.parse("11:00"));
////        shiftDayPrototype28.setEndTimeOfDay(timeFormatter.parse("13:00"));
////        shiftDayPrototype28.setShift(shift2);
////
////        ShiftDayPrototype shiftDayPrototype29 = new ShiftDayPrototype();
////        shiftDayPrototype29.setDayType(DayType.START_DAY);
////        shiftDayPrototype29.setNetHours(2);
////        shiftDayPrototype29.setStartTimeOfDay(timeFormatter.parse("10:00"));
////        shiftDayPrototype29.setEndTimeOfDay(timeFormatter.parse("12:00"));
////        shiftDayPrototype29.setShift(shift2);
////
////        ShiftDayPrototype shiftDayPrototype210 = new ShiftDayPrototype();
////        shiftDayPrototype210.setDayType(DayType.END_DAY);
////        shiftDayPrototype210.setNetHours(2);
////        shiftDayPrototype210.setStartTimeOfDay(timeFormatter.parse("12:00"));
////        shiftDayPrototype210.setEndTimeOfDay(timeFormatter.parse("14:00"));
////        shiftDayPrototype210.setShift(shift2);
////
////        shiftDayPrototypeRepository.save(shiftDayPrototype21);
////        shiftDayPrototypeRepository.save(shiftDayPrototype22);
////        shiftDayPrototypeRepository.save(shiftDayPrototype23);
////        shiftDayPrototypeRepository.save(shiftDayPrototype24);
////        shiftDayPrototypeRepository.save(shiftDayPrototype25);
////        shiftDayPrototypeRepository.save(shiftDayPrototype26);
////        shiftDayPrototypeRepository.save(shiftDayPrototype27);
////        shiftDayPrototypeRepository.save(shiftDayPrototype28);
////        shiftDayPrototypeRepository.save(shiftDayPrototype29);
////        shiftDayPrototypeRepository.save(shiftDayPrototype210);
////
////
////        AccumulatedObHours accumulatedObHours211 = new AccumulatedObHours();
////        accumulatedObHours211.setAccumulatedHours(1);
////        accumulatedObHours211.setServiceCategory(commanderCategory);
////        accumulatedObHours211.setShiftDayPrototype(shiftDayPrototype21);
////
////        AccumulatedObHours accumulatedObHours212 = new AccumulatedObHours();
////        accumulatedObHours212.setAccumulatedHours(0);
////        accumulatedObHours212.setServiceCategory(decksmenCategory);
////        accumulatedObHours212.setShiftDayPrototype(shiftDayPrototype21);
////
////
////        AccumulatedObHours accumulatedObHours221 = new AccumulatedObHours();
////        accumulatedObHours221.setAccumulatedHours(1);
////        accumulatedObHours221.setServiceCategory(commanderCategory);
////        accumulatedObHours221.setShiftDayPrototype(shiftDayPrototype22);
////
////        AccumulatedObHours accumulatedObHours222 = new AccumulatedObHours();
////        accumulatedObHours222.setAccumulatedHours(0);
////        accumulatedObHours222.setServiceCategory(decksmenCategory);
////        accumulatedObHours222.setShiftDayPrototype(shiftDayPrototype22);
////
////        AccumulatedObHours accumulatedObHours231 = new AccumulatedObHours();
////        accumulatedObHours231.setAccumulatedHours(1);
////        accumulatedObHours231.setServiceCategory(commanderCategory);
////        accumulatedObHours231.setShiftDayPrototype(shiftDayPrototype23);
////
////        AccumulatedObHours accumulatedObHours232 = new AccumulatedObHours();
////        accumulatedObHours232.setAccumulatedHours(0);
////        accumulatedObHours232.setServiceCategory(decksmenCategory);
////        accumulatedObHours232.setShiftDayPrototype(shiftDayPrototype23);
////
////        AccumulatedObHours accumulatedObHours241 = new AccumulatedObHours();
////        accumulatedObHours241.setAccumulatedHours(1);
////        accumulatedObHours241.setServiceCategory(commanderCategory);
////        accumulatedObHours241.setShiftDayPrototype(shiftDayPrototype24);
////
////        AccumulatedObHours accumulatedObHours242 = new AccumulatedObHours();
////        accumulatedObHours242.setAccumulatedHours(0);
////        accumulatedObHours242.setServiceCategory(decksmenCategory);
////        accumulatedObHours242.setShiftDayPrototype(shiftDayPrototype24);
////
////        AccumulatedObHours accumulatedObHours251 = new AccumulatedObHours();
////        accumulatedObHours251.setAccumulatedHours(1);
////        accumulatedObHours251.setServiceCategory(commanderCategory);
////        accumulatedObHours251.setShiftDayPrototype(shiftDayPrototype25);
////
////        AccumulatedObHours accumulatedObHours252 = new AccumulatedObHours();
////        accumulatedObHours252.setAccumulatedHours(0);
////        accumulatedObHours252.setServiceCategory(decksmenCategory);
////        accumulatedObHours252.setShiftDayPrototype(shiftDayPrototype25);
////
////        AccumulatedObHours accumulatedObHours261 = new AccumulatedObHours();
////        accumulatedObHours261.setAccumulatedHours(8);
////        accumulatedObHours261.setServiceCategory(commanderCategory);
////        accumulatedObHours261.setShiftDayPrototype(shiftDayPrototype26);
////
////        AccumulatedObHours accumulatedObHours262 = new AccumulatedObHours();
////        accumulatedObHours262.setAccumulatedHours(0);
////        accumulatedObHours262.setServiceCategory(decksmenCategory);
////        accumulatedObHours262.setShiftDayPrototype(shiftDayPrototype26);
////
////        AccumulatedObHours accumulatedObHours271 = new AccumulatedObHours();
////        accumulatedObHours271.setAccumulatedHours(8);
////        accumulatedObHours271.setServiceCategory(commanderCategory);
////        accumulatedObHours271.setShiftDayPrototype(shiftDayPrototype27);
////
////        AccumulatedObHours accumulatedObHours272 = new AccumulatedObHours();
////        accumulatedObHours272.setAccumulatedHours(0);
////        accumulatedObHours272.setServiceCategory(decksmenCategory);
////        accumulatedObHours272.setShiftDayPrototype(shiftDayPrototype27);
////
////        AccumulatedObHours accumulatedObHours281 = new AccumulatedObHours();
////        accumulatedObHours281.setAccumulatedHours(8);
////        accumulatedObHours281.setServiceCategory(commanderCategory);
////        accumulatedObHours281.setShiftDayPrototype(shiftDayPrototype28);
////
////        AccumulatedObHours accumulatedObHours282 = new AccumulatedObHours();
////        accumulatedObHours282.setAccumulatedHours(8);
////        accumulatedObHours282.setServiceCategory(decksmenCategory);
////        accumulatedObHours282.setShiftDayPrototype(shiftDayPrototype28);
////
////        AccumulatedObHours accumulatedObHours291 = new AccumulatedObHours();
////        accumulatedObHours291.setAccumulatedHours(1);
////        accumulatedObHours291.setServiceCategory(commanderCategory);
////        accumulatedObHours291.setShiftDayPrototype(shiftDayPrototype29);
////
////        AccumulatedObHours accumulatedObHours292 = new AccumulatedObHours();
////        accumulatedObHours292.setAccumulatedHours(0);
////        accumulatedObHours292.setServiceCategory(decksmenCategory);
////        accumulatedObHours292.setShiftDayPrototype(shiftDayPrototype29);
////
////        AccumulatedObHours accumulatedObHours2101 = new AccumulatedObHours();
////        accumulatedObHours2101.setAccumulatedHours(2);
////        accumulatedObHours2101.setServiceCategory(commanderCategory);
////        accumulatedObHours2101.setShiftDayPrototype(shiftDayPrototype210);
////
////        AccumulatedObHours accumulatedObHours2102 = new AccumulatedObHours();
////        accumulatedObHours2102.setAccumulatedHours(0);
////        accumulatedObHours2102.setServiceCategory(decksmenCategory);
////        accumulatedObHours2102.setShiftDayPrototype(shiftDayPrototype210);
////
////        accumulatedObHoursRepository.save(accumulatedObHours211);
////        accumulatedObHoursRepository.save(accumulatedObHours212);
////        accumulatedObHoursRepository.save(accumulatedObHours221);
////        accumulatedObHoursRepository.save(accumulatedObHours222);
////        accumulatedObHoursRepository.save(accumulatedObHours231);
////        accumulatedObHoursRepository.save(accumulatedObHours232);
////        accumulatedObHoursRepository.save(accumulatedObHours241);
////        accumulatedObHoursRepository.save(accumulatedObHours242);
////        accumulatedObHoursRepository.save(accumulatedObHours251);
////        accumulatedObHoursRepository.save(accumulatedObHours252);
////        accumulatedObHoursRepository.save(accumulatedObHours261);
////        accumulatedObHoursRepository.save(accumulatedObHours262);
////        accumulatedObHoursRepository.save(accumulatedObHours271);
////        accumulatedObHoursRepository.save(accumulatedObHours272);
////        accumulatedObHoursRepository.save(accumulatedObHours281);
////        accumulatedObHoursRepository.save(accumulatedObHours282);
////        accumulatedObHoursRepository.save(accumulatedObHours291);
////        accumulatedObHoursRepository.save(accumulatedObHours292);
////        accumulatedObHoursRepository.save(accumulatedObHours2101);
////        accumulatedObHoursRepository.save(accumulatedObHours2102);
////
////        UnionContract decksmenContract = new UnionContract();
////        decksmenContract.setName("Kollektivavtal däcksmän 2017");
////        decksmenContract.setStartDate(DateTimeUtils.dateFromDateString("2017-01-01"));
////        decksmenContract.setEndDate(DateTimeUtils.dateFromDateString("2022-12-31"));
////        decksmenContract.setMonthlyWorkHours(35);
////        decksmenContract.setServiceCategory(decksmenCategory);
////        decksmenContract.setObRegularCoeff(0.2);
////        decksmenContract.setObHolidayCoeff(0.5);
////        decksmenContract.setOvertimeRegulaCoeff(1.4);
////        decksmenContract.setOvertimeHolidayCoeff(2.0);
////
////
////        UnionContract commanderContract = new UnionContract();
////        commanderContract.setName("Kollektivavtal befälhavare 2017");
////        commanderContract.setStartDate(DateTimeUtils.dateFromDateString("2017-01-01"));
////        commanderContract.setEndDate(DateTimeUtils.dateFromDateString("2022-12-31"));
////        commanderContract.setMonthlyWorkHours(35);
////        commanderContract.setServiceCategory(commanderCategory);
////        commanderContract.setObRegularCoeff(0.4);
////        commanderContract.setObHolidayCoeff(0.5);
////        commanderContract.setOvertimeRegulaCoeff(1.5);
////        commanderContract.setOvertimeHolidayCoeff(2.5);
////
////        unionContractRepository.save(decksmenContract);
////        unionContractRepository.save(commanderContract);
////
////
////        Withdrawal withdrawal1 = new Withdrawal();
////        withdrawal1.setEmployee(employee1);
////        withdrawal1.setDate(new Date());
////        withdrawal1.setAmount(5);
////
////        withdrawalRepository.save(withdrawal1);
//        loadHolidays();
//
//    }
//
//    private void loadHolidays() {
//
//        List<Holiday> holidays = new ArrayList<>();
//        int year = 2017;
//        for (int i = 0; i < 10; i++) {
//            StringBuilder sb = new StringBuilder("http://kayaposoft.com/enrico/json/v1.0/?action=getPublicHolidaysForYear&year=");
//            sb.append(year).append("&country=swe");
//
//            List<RemoteHoliday> remoteHolidays = ClientBuilder.newClient()
//                    .target(sb.toString())
//                    .request(MediaType.APPLICATION_JSON)
//                    .get(new GenericType<List<RemoteHoliday>>() {
//                    });
//
//
//            remoteHolidays.forEach(remoteHoliday -> {
//                Date date = new DateTime(remoteHoliday.getDate().getYear(), remoteHoliday.getDate().getMonth(), remoteHoliday.getDate().getDay(), 0, 0, 0).toDate();
//                Holiday holiday = new Holiday();
//                holiday.setDate(date);
//                holiday.setName(remoteHoliday.getLocalName());
//                holidays.add(holiday);
//            });
//            year++;
//        }
//
//        holidayRepository.save(holidays);
//
//        User user = userRepository.findByUsername("admin");
//        System.out.println(user.getRoles());
//        Employee employee = employeeRepository.findOne(1L);
//        System.out.println(user.getEmployee());
//        System.out.println(employee.getUser());
//    }
//}
