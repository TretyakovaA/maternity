package pro.sky.maternity.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.PhotoSize;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.GetFile;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.GetFileResponse;
import com.pengrad.telegrambot.response.SendResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pro.sky.maternity.exception.MaternityHospitalNotFoundException;
import pro.sky.maternity.mapper.MaternityHospitalDtoMapper;
import pro.sky.maternity.mapper.UserDtoMapper;
import pro.sky.maternity.model.DailyMail;
import pro.sky.maternity.model.MaternityHospital;
import pro.sky.maternity.model.Reports;
import pro.sky.maternity.model.User;
import pro.sky.maternity.repository.DailyMailRepository;
import pro.sky.maternity.repository.MaternityHospitalRepository;
import pro.sky.maternity.repository.ReportsRepository;
import pro.sky.maternity.repository.UserRepository;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class TelegramUpdateHandler {

    private final Logger logger = LoggerFactory.getLogger(TelegramUpdateHandler.class);

    private final TelegramBot telegramBot;

    private int menuNumber = -1;

    private Properties properties = null;

    @Value("${specialist.id}")
    private int specialistId;

    @Value("${max.days.count}")
    private int maxDaysCount;

    //меню0
    private String maternityName;
    public final String MENU0_BUTTON1 = "Роддом №40";
    public final String MENU0_BUTTON2 = "Роддом МОНИИАГ";


    //меню1
    public final String MENU1_BUTTON1 = "Узнать информацию о роддоме";
    public final String MENU1_BUTTON2 = "Что нужно взять с собой в роддом";
    public final String MENU1_BUTTON3 = "Послеродовое сопровождение";
    public final String MENU1_BUTTON4 = "Связаться со специалистом";
    public final String MENU1_BUTTON5 = "Вернуться в предыдущее меню";

    //menu2

    public final String MENU2_BUTTON1 = "Рассказать о роддоме подробнее";
    public final String MENU2_BUTTON2 = "График работы роддома";
    public final String MENU2_BUTTON3 = "Адрес, схема проезда";
    public final String MENU2_BUTTON5 = "Что взять с собой в роддом";
    public final String MENU2_BUTTON6 = "Связаться со специалистом";
    public final String MENU2_BUTTON7 = "Вернуться в предыдущее меню";

    //menu3

    public final String MENU3_BUTTON1 = "Какие документы взять?";
    public final String MENU3_BUTTON2 = "Пребывание в роддоме";
    public final String MENU3_BUTTON3 = "Забираем малыша домой. Рекомендации";
    public final String MENU3_BUTTON4 = "Список рекомендаций по обустройству дома для малыша";
    public final String MENU3_BUTTON5 = "Список рекомендаций по обустройству дома для мамы";
    public final String MENU3_BUTTON6 = "Советы неонатолога по уходу за ребенком в первые недели жизни";
    public final String MENU3_BUTTON7 = "Контактные данные рекомендованных педиатров";
    public final String MENU3_BUTTON8 = "Записаться на послеродовое сопровождение";
    public final String MENU3_BUTTON9 = "Связаться со специалистом";
    public final String MENU3_BUTTON10 = "Вернуться в предыдущее меню";

    //Меню4 (запись на сопровождение)
    public final String MENU4_BUTTON1 = "Записаться (указать дату рождения малыша)";
    public final String MENU4_BUTTON3 = "Вернуться в предыдущее меню";

    //Меню 5 (узнать о самочувствии пользователя)
    public final String MENU5_BUTTON1 = "Сообщить о самочувствии";
    public final String MENU5_BUTTON2 = "Отписаться от рассылки";

    private final MaternityHospitalRepository maternityHospitalRepository;
    private final MaternityHospitalDtoMapper maternityHospitalDtoMapper;

    private final UserRepository userRepository;
    private final DailyMailRepository dailyMailRepository;

    private final ReportsRepository reportsRepository;
    private final UserDtoMapper userDtoMapper;
    private Pattern pattern = Pattern.compile("([0-9\\.]{10})");

    public TelegramUpdateHandler(TelegramBot telegramBot, MaternityHospitalRepository maternityHospitalRepository, MaternityHospitalDtoMapper maternityHospitalDtoMapper, UserRepository userRepository, DailyMailRepository dailyMailRepository, ReportsRepository reportsRepository, UserDtoMapper userDtoMapper) {
        this.telegramBot = telegramBot;
        this.maternityHospitalRepository = maternityHospitalRepository;
        this.maternityHospitalDtoMapper = maternityHospitalDtoMapper;
        this.userRepository = userRepository;
        this.dailyMailRepository = dailyMailRepository;
        this.reportsRepository = reportsRepository;
        this.userDtoMapper = userDtoMapper;
    }

    public void callToSpecialist(Update update) {
        if (update.message().chat().username() != null) {
            telegramBot.execute(new SendMessage(specialistId,
                    "Уважаемый администратор роддома " + maternityName +
                            "! Просьба связаться с пациентом @" + update.message().chat().username()));
        } else {
            telegramBot.execute(new SendMessage(update.message().chat().id(),
                    "Уважаемый пациент " +
                            "! Просьба указать ваш username в профиле телеграм "));
        }
    }

    public void registerToMaternitySupport(Update update) {
        MaternityHospital userMaternity = new MaternityHospital();
        switch (maternityName) {
            //Роддом40
            case MENU0_BUTTON1:
                userMaternity = maternityHospitalRepository.findById(3L).orElseThrow(()
                        -> {
                    throw new MaternityHospitalNotFoundException(3L);
                });
                break;
            //МОНИИАГ
            case MENU0_BUTTON2:
                userMaternity = maternityHospitalRepository.findById(2L).orElseThrow(()
                        -> {
                    throw new MaternityHospitalNotFoundException(2L);
                });
                break;
        }
        System.out.println(userMaternity.getId() + " " + userMaternity.getName());

        if (update.message().chat().username() != null) {
            userRepository.save(new User(update.message().chat().id(), update.message().chat().username(),
                    LocalDateTime.parse(update.message().text() + " 00:00", DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")), userMaternity));
        } else {
            telegramBot.execute(new SendMessage(update.message().chat().id(),
                    "Уважаемый пациент " +
                            "! Просьба указать ваш username в профиле телеграм "));
        }
    }

    public void unRegisterToMaternitySupport(Update update) {
        if (update.message().chat().username() != null) {
            List<User> usersTodelete = userRepository.findByName(update.message().chat().username());
            if (usersTodelete != null && usersTodelete.size()>0) {
                userRepository.deleteById(usersTodelete.get(0).getId());
                telegramBot.execute(new SendMessage(update.message().chat().id(),
                        "Уважаемый пациент " +
                                "! Вы удалены из рассылки "));
            } else {
                telegramBot.execute(new SendMessage(update.message().chat().id(),
                        "Уважаемый пациент " +
                                "! Ваш username не найден "));
            }
        } else {
            telegramBot.execute(new SendMessage(update.message().chat().id(),
                    "Уважаемый пациент " +
                            "! Ваш username не найден "));
        }
        menuNumber = -1;
    }

    public void sendInfoAboutHealth (Update update) {
        String pathPhoto = "";
        if (update.message().chat().username() != null) {
            List<User> users = userRepository.findByName(update.message().chat().username());
            if (users != null && users.size()>0) {
                User user = users.get(0);


//                //получение фото
//                //GetFile getFile = new GetFile(update.message().photo()[0].fileId());
//                if (update.message().photo().length>0)
//                {
//                    PhotoSize [] photos = update.message().photo();
//                    int count = 1;
//                    for (PhotoSize photo : photos)
//                    {
//                        GetFile getFile = new GetFile(photo.fileId());
//                        try
//                        {
//                           GetFileResponse file = telegramBot.execute(getFile); //tg file obj
//                            telegramBot. .getFileContent(file, new java.io.File("photos/photo" + count + ".png"));
//                            count++;
//                        } catch (Exception e)
//                        {
//                            e.printStackTrace();
//                        }
//                    }
//                }

                //long chatId, LocalDateTime date, String text, String photo, User user
                Reports report = new Reports(update.message().chat().id(),
                        LocalDateTime.now(), update.message().text(),null, user);
                if (reportsRepository.save(report) != null){
                    telegramBot.execute(new SendMessage(update.message().chat().id(),
                            "Уважаемый пациент " +
                                    ", ваш отчет сохранен! "));
                }
            } else {
                telegramBot.execute(new SendMessage(update.message().chat().id(),
                        "Уважаемый пациент " +
                                "! Ваш username не найден "));
            }
        } else {
            telegramBot.execute(new SendMessage(update.message().chat().id(),
                    "Уважаемый пациент " +
                            "! Ваш username не найден "));
        }
        menuNumber = -1;
    }

    public void showMenu(long chatId) {
        System.out.println(chatId);
        switch (menuNumber) {
            case 0:
                telegramBot.execute(new SendMessage(chatId, "Добро пожаловать! Какой роддом Вас интересует?")
                        .replyMarkup(new ReplyKeyboardMarkup(new String[]{MENU0_BUTTON1}, new String[]{MENU0_BUTTON2})));
                menuNumber = 0;
                break;
            case 1:
                telegramBot.execute(new SendMessage(chatId, "Вы находитесь в меню роддома: " + maternityName)
                        .replyMarkup(new ReplyKeyboardMarkup(new String[]{MENU1_BUTTON1},
                                new String[]{MENU1_BUTTON2}, new String[]{MENU1_BUTTON3}, new String[]{MENU1_BUTTON4},
                                new String[]{MENU1_BUTTON5})));
                break;
            case 2:
                telegramBot.execute(new SendMessage(chatId, "Информация о роддоме: " + maternityName)
                        .replyMarkup(new ReplyKeyboardMarkup(new String[]{MENU2_BUTTON1},
                                new String[]{MENU2_BUTTON2}, new String[]{MENU2_BUTTON3}
                                , new String[]{MENU2_BUTTON5}, new String[]{MENU2_BUTTON6}, new String[]{MENU2_BUTTON7})));
                break;
            case 3:
                telegramBot.execute(new SendMessage(chatId, "Частые вопросы")
                        .replyMarkup(new ReplyKeyboardMarkup(new String[]{MENU3_BUTTON1},
                                new String[]{MENU3_BUTTON2}, new String[]{MENU3_BUTTON3}
                                , new String[]{MENU3_BUTTON4}, new String[]{MENU3_BUTTON5}, new String[]{MENU3_BUTTON6}
                                , new String[]{MENU3_BUTTON7}, new String[]{MENU3_BUTTON8}
                                , new String[]{MENU3_BUTTON9}, new String[]{MENU3_BUTTON10})));
                break;
            case 4:
                telegramBot.execute(new SendMessage(chatId, "Запись на сопровождение")
                        .replyMarkup(new ReplyKeyboardMarkup(new String[]{MENU4_BUTTON1}, new String[]{MENU4_BUTTON3})));
                break;
            case  5:
                telegramBot.execute(new SendMessage(chatId, "Дорогая мама, нам важно знать как вы с малышом себя чувствуете!")
                        .replyMarkup(new ReplyKeyboardMarkup(new String[]{MENU5_BUTTON1},new String[]{MENU5_BUTTON2})));
                break;
        }

    }

    /**
     * Фасад, основная логика построения меню бота и обработки статусов
     * По окончанию метода, отправляем сообщение или редактируем, сохраняем ответ полученный от телеги
     *
     * @param update Update
     */
    public void processUpdate(Update update) throws IOException {
        logger.info("Processing update: {}", update);
        // Process your updates here
        long chatId = update.message().chat().id();
        if (menuNumber == -1 || update.message().text().equals("/start")) {
            menuNumber = 0;
            showMenu(chatId);

        } else if (menuNumber == 0) {
            switch (update.message().text()) {
                // выбираем файлы свойств в завивисмости от имени роддома
                //Роддом 40
                case MENU0_BUTTON1:
                    maternityName = MENU0_BUTTON1;
                    // telegramBot.execute(new SendMessage(chatId, "Узнать информацию о роддоме: "+ maternityName));
                    properties = PropertiesLoaderUtils.loadProperties(new EncodedResource
                            (new ClassPathResource("info.40.properties"), StandardCharsets.UTF_8));
                    menuNumber = 1;
                    showMenu(chatId);
                    break;
                // роддом МОНИИАГ
                case MENU0_BUTTON2:
                    maternityName = MENU0_BUTTON2;
                    // telegramBot.execute(new SendMessage(chatId, "Узнать информацию о роддоме: " + maternityName));
                    properties = PropertiesLoaderUtils.loadProperties(new EncodedResource
                            (new ClassPathResource("info.moniiag.properties"), StandardCharsets.UTF_8));
                    menuNumber = 1;
                    showMenu(chatId);
                    break;
            }
        } else if (menuNumber == 1) {
//                telegramBot.execute(new SendMessage(chatId,message).replyMarkup(new ReplyKeyboardMarkup(new String[]{MENU1_BUTTON1},
//                        new String[]{MENU1_BUTTON2}, new String[]{MENU1_BUTTON3}, new String[]{MENU1_BUTTON4})));
            //String text = update.message().text();

            switch (update.message().text()) {
                //public final String MENU1_BUTTON1 = "Узнать информацию о роддоме";
                case MENU1_BUTTON1:
                    //telegramBot.execute(new SendMessage(chatId, "(Узнать информацию о роддоме), Открыть меню 2"));
                    menuNumber = 2;
                    showMenu(chatId);
                    break;
                //"Что нужно взять с собой в роддом";
                case MENU1_BUTTON2:
                    //telegramBot.execute(new SendMessage(chatId, "(Что нужно взять с собой в роддом), Открыть меню 3"));
                    menuNumber = 3;
                    showMenu(chatId);
                    break;
                //"Послеродовое сопровождение"
                case MENU1_BUTTON3:
                    //telegramBot.execute(new SendMessage(chatId, "(Послеродовое сопровождение), Открыть меню 3"));
                    menuNumber = 3;
                    showMenu(chatId);
                    break;
                //"Связаться со специалистом"
                case MENU1_BUTTON4:
                    telegramBot.execute(new SendMessage(chatId, "Ваше сообщение принято. Специалист свяжется с вами в ближайшее время"));
                    callToSpecialist(update);
                    menuNumber = 1;
                    showMenu(chatId);
                    break;
                //"Вернуться в предыдущее меню";
                case MENU1_BUTTON5:
                    //  telegramBot.execute(new SendMessage(chatId, "(Вернуться в предыдущее меню), Открыть меню 0"));
                    menuNumber = 0;
                    showMenu(chatId);
                    break;
                default:
                    telegramBot.execute(new SendMessage(chatId, "Ваше сообщение принято. Специалист свяжется с вами в ближайшее время"));
                    callToSpecialist(update);
                    menuNumber = 1;
                    showMenu(chatId);
                    break;
            }
        } else if (menuNumber == 2) {

            switch (update.message().text()) {
                //Рассказать о роддоме подробнее";
                case MENU2_BUTTON1:
                    telegramBot.execute(new SendMessage(chatId, properties.getProperty("details.maternity.info")));
                    menuNumber = 2;
                    showMenu(chatId);
                    break;
                //""График работы роддома"";
                case MENU2_BUTTON2:
                    telegramBot.execute(new SendMessage(chatId, properties.getProperty("grafik.maternity.info")));
                    menuNumber = 2;
                    showMenu(chatId);
                    break;
                //"Адрес, схема проезда "
                case MENU2_BUTTON3:
                    telegramBot.execute(new SendMessage(chatId, properties.getProperty("address.maternity.info")));
                    menuNumber = 2;
                    showMenu(chatId);
                    break;
                //"Что взять с собой в роддом";
                case MENU2_BUTTON5:
                    // telegramBot.execute(new SendMessage(chatId, "(Что взять с собой в роддом), открыть меню 3"));
                    menuNumber = 3;
                    showMenu(chatId);
                    break;
                //"Связаться со специалистом"
                case MENU2_BUTTON6:
                    telegramBot.execute(new SendMessage(chatId, "Ваше сообщение принято. Специалист свяжется с вами в ближайшее время"));
                    callToSpecialist(update);
                    menuNumber = 2;
                    showMenu(chatId);
                    break;
                //"Вернуться в предыдущее меню";
                case MENU2_BUTTON7:
                    // telegramBot.execute(new SendMessage(chatId, "(Вернуться в предыдущее меню), Открыть меню 1"));
                    menuNumber = 1;
                    showMenu(chatId);
                    break;
                default:
                    telegramBot.execute(new SendMessage(chatId, "Ваше сообщение принято. Специалист свяжется с вами в ближайшее время"));
                    callToSpecialist(update);
                    menuNumber = 2;
                    showMenu(chatId);
                    break;
            }

        } else if (menuNumber == 3) {
            switch (update.message().text()) {
                //"Какие документы взять?";
                case MENU3_BUTTON1:
                    telegramBot.execute(new SendMessage(chatId, properties.getProperty("documents.info")));
                    menuNumber = 3;
                    showMenu(chatId);
                    break;
                //"Первые дни в роддоме";
                case MENU3_BUTTON2:
                    telegramBot.execute(new SendMessage(chatId, properties.getProperty("staying.maternity.info")));
                    menuNumber = 3;
                    showMenu(chatId);
                    break;
                //"Забираем малыша домой. Рекомендации"
                case MENU3_BUTTON3:
                    telegramBot.execute(new SendMessage(chatId, properties.getProperty("comming.home.info")));
                    menuNumber = 3;
                    showMenu(chatId);
                    break;
                //"Список рекомендаций по обустройству дома для малыша"
                case MENU3_BUTTON4:
                    telegramBot.execute(new SendMessage(chatId, properties.getProperty("advices.baby.home.info")));
                    menuNumber = 3;
                    showMenu(chatId);
                    break;
                //"Список рекомендаций по обустройству дома для мамы"
                case MENU3_BUTTON5:
                    telegramBot.execute(new SendMessage(chatId, properties.getProperty("advices.mother.home.info")));
                    menuNumber = 3;
                    showMenu(chatId);
                    break;
                //"Советы неонатолога по уходу за ребенком в первые недели жизни"
                case MENU3_BUTTON6:
                    telegramBot.execute(new SendMessage(chatId, properties.getProperty("neonatologist.advices.info")));
                    menuNumber = 3;
                    showMenu(chatId);
                    break;
                //"Контактные данные рекомендованных педиатров"
                case MENU3_BUTTON7:
                    telegramBot.execute(new SendMessage(chatId, properties.getProperty("pediatrician.advices.info")));
                    menuNumber = 3;
                    showMenu(chatId);
                    break;
                //"Записаться на послеродовое сопровождение"
                case MENU3_BUTTON8:
                    List<User> users = userRepository.findAll();
                    Set<Long> chatIds = new HashSet<>();
                    if (users != null && users.size() != 0) {
                        for (User u : users) {
                            chatIds.add(u.getChatId());
                        }
                        if (chatIds.contains(update.message().chat().id())) {
                            telegramBot.execute(new SendMessage(update.message().chat().id(),
                                    "Уважаемый пациент " +
                                            ", Вы уже записаны на сопровождение! "));
                            menuNumber = 3;
                            showMenu(chatId);
                            break;
                        }
                    }
                    // telegramBot.execute(new SendMessage(chatId, "Записаться на послеродовое сопровождение"));
                    menuNumber = 4;
                    showMenu(chatId);
                    break;
                //"Связаться со специалистом"
                case MENU3_BUTTON9:
                    telegramBot.execute(new SendMessage(chatId, "Ваше сообщение принято. Специалист свяжется с вами в ближайшее время"));
                    callToSpecialist(update);
                    menuNumber = 3;
                    showMenu(chatId);
                    break;
                //"Вернуться в предыдущее меню";
                case MENU3_BUTTON10:
                    // telegramBot.execute(new SendMessage(chatId, "(Вернуться в предыдущее меню), Открыть меню 2"));
                    menuNumber = 2;
                    showMenu(chatId);
                    break;
                default:
                    telegramBot.execute(new SendMessage(chatId, "Ваше сообщение принято. Специалист свяжется с вами в ближайшее время"));
                    callToSpecialist(update);
                    menuNumber = 3;
                    showMenu(chatId);
                    break;
            }
        } else if (menuNumber == 4) {
            switch (update.message().text()) {
                //Записаться
                //Указать дату рождения малыша
                case MENU4_BUTTON1:
                    SendResponse response = telegramBot.execute(new SendMessage(chatId,
                            "Пожалуйста, введите дату и время рождения малыша в формате: дд.мм.гггг"));
                    menuNumber = 4;
                    showMenu(chatId);
                    break;
                //Возврат в предыдущее меню
                case MENU4_BUTTON3:
                    menuNumber = 3;
                    showMenu(chatId);
                    break;
                default:
                    Matcher matcher = pattern.matcher(update.message().text());
                    if (matcher.matches()) {
                        String date = matcher.group(1);
                        registerToMaternitySupport(update);
                        telegramBot.execute(new SendMessage(chatId, "Спасибо, вы записаны на сопровождение"));
                        telegramBot.execute(new SendMessage(chatId, "Первое письмо отдела заботы вы получите уже завтра!"));
                        menuNumber = 3;
                        showMenu(chatId);
                    } else {
                        telegramBot.execute(new SendMessage(chatId, " Ошибка! Введите сообщение в формате: дд.мм.гггг"));
                        menuNumber = 4;
                        showMenu(chatId);
                    }

                    //telegramBot.execute(new SendMessage(chatId, "Ваше сообщение принято. Специалист свяжется с вами в ближайшее время"));
                    //callToSpecialist(update);

                    break;
            }
        } else if (menuNumber == 5) {
            //Спросить пользователя о самочувствии
            switch (update.message().text()) {
                //Сообщить о самочувствии
                case MENU5_BUTTON1:
                    telegramBot.execute(new SendMessage(chatId, "Дорогая мама, напиши, пожалуйста подробнее как вы себя чувствуете. " +
                            "Мы рады увидеть новое фото малыша, можешь прикрепить его к сообщению!"));
                    break;
                    //Отписаться от рассылки
                case MENU5_BUTTON2:
                    unRegisterToMaternitySupport (update);
                    break;
                default:
                    sendInfoAboutHealth (update);
                    break;
            }
        }
    }

    /**
     * Инициализация сообщения
     *
     * @param update событие на сервере
     * @return message текст написанный в чат
     */
    private String getMessage(Update update) {
        String message = "^-^";
        if (update.message() != null && update.message().text() != null) {
            message = update.message().text();
        }
        return message;
    }

    //тестовый раз в минуту
    @Scheduled(cron = "0 0/1 * * * *")
    //ежедневно в 12 дня
    //@Scheduled(cron = "0 0 12 * * *")
    public void testDailyMail() {
        List<User> users = userRepository.findAll();
        if (users == null || users.size() == 0) {
            return;
        }

        for (User user : users) {
            if (user.getChildBirthday().isAfter(
                    LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES).minusDays(maxDaysCount))) {
                long dayNumber = Duration.between(LocalDateTime.now(), user.getChildBirthday()).toDays()+1;
                DailyMail mail = dailyMailRepository.findById(dayNumber).orElseThrow();
                telegramBot.execute(new SendMessage(user.getChatId(),mail.getInfo()));
                menuNumber = 5;
                showMenu(user.getChatId());
            }
        }
    }
}
