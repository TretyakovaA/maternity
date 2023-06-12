package pro.sky.maternity.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.maternity.dto.MaternityHospitalDto;
import pro.sky.maternity.mapper.MaternityHospitalDtoMapper;
import pro.sky.maternity.repository.MaternityHospitalRepository;
//import pro.sky.telegrambot.model.NotificationTasks;
//import pro.sky.telegrambot.repository.NotificationTasksRepository;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    private Pattern pattern = Pattern.compile("([0-9\\.\\:\\s]{16})(\\s)([\\W+]+)");
    private final TelegramBot telegramBot;

    private final MaternityHospitalRepository maternityHospitalRepository;
    private final MaternityHospitalDtoMapper maternityHospitalDtoMapper;

    public TelegramBotUpdatesListener(TelegramBot telegramBot, MaternityHospitalRepository maternityHospitalRepository, MaternityHospitalDtoMapper maternityHospitalDtoMapper) {
        this.telegramBot = telegramBot;
        this.maternityHospitalRepository = maternityHospitalRepository;
        this.maternityHospitalDtoMapper = maternityHospitalDtoMapper;
    }

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            logger.info("Processing update: {}", update);
            // Process your updates here
            long chatId = update.message().chat().id();
            if (update.message().text().equals("/start")) {
                String message = "Добро пожаловать! Какой роддом Вас интересует? /n";
                List<MaternityHospitalDto> maternities = maternityHospitalDtoMapper.toDtos(maternityHospitalRepository.findAll());
                for (int i = 0; i < maternities.size(); i++) {
message = message + maternities.get(i).getNumber() +  "- роддом №" + maternities.get(i).getNumber()+" /n";
                }
                SendResponse response = telegramBot.execute(new SendMessage(chatId, message));
            } else {
                Matcher matcher = pattern.matcher(update.message().text());
                if (matcher.matches()) {
                    String date = matcher.group(1);
                    String item = matcher.group(3);
                   /* NotificationTasks task = new NotificationTasks(update.message().chat().id(), item, LocalDateTime.parse(date, DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")));
                    notificationTasksRepository.save(task);
                    SendResponse response = telegramBot.execute(new SendMessage(chatId, "Спасибо, обязательно напомним!"));*/
                } else {
                    SendResponse response = telegramBot.execute(new SendMessage(chatId, " Ошибка! Введите сообщение в формате: дд.мм.гггг чч:мм текст напоминалки"));
                }

            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

   /* @Scheduled(cron = "0 0/1 * * * *")
    public void testNotifications() {
        List<NotificationTasks> allTasks = notificationTasksRepository.findNotificationTasksByNotificationTime(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
        for (NotificationTasks task: allTasks){
            SendResponse response = telegramBot.execute(new SendMessage(task.getChatId(), task.getNotification()));
        }
    }*/


}