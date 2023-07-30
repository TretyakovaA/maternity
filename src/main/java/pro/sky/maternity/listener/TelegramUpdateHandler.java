package pro.sky.maternity.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Service;
import pro.sky.maternity.mapper.MaternityHospitalDtoMapper;
import pro.sky.maternity.repository.MaternityHospitalRepository;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

@Service
public class TelegramUpdateHandler {

    private final Logger logger = LoggerFactory.getLogger(TelegramUpdateHandler.class);

    private final TelegramBot telegramBot;

    private int menuNumber = 0;

    private Properties properties = null;

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
    public final String MENU2_BUTTON3 = "Адрес, схема проезда ";
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


    private final MaternityHospitalRepository maternityHospitalRepository;
    private final MaternityHospitalDtoMapper maternityHospitalDtoMapper;

    public TelegramUpdateHandler(TelegramBot telegramBot, MaternityHospitalRepository maternityHospitalRepository, MaternityHospitalDtoMapper maternityHospitalDtoMapper) {
        this.telegramBot = telegramBot;
        this.maternityHospitalRepository = maternityHospitalRepository;
        this.maternityHospitalDtoMapper = maternityHospitalDtoMapper;
    }

    public void showMenu(long chatId) {
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
                        .replyMarkup(new ReplyKeyboardMarkup(new String[]{MENU1_BUTTON1},
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
        if (update.message().text().equals("/start")) {
            menuNumber = 0;
            showMenu(chatId);
               /* List<MaternityHospitalDto> maternities = maternityHospitalDtoMapper.toDtos(maternityHospitalRepository.findAll());
                for (int i = 0; i < maternities.size(); i++) {
                    message = message + maternities.get(i).getNumber() +  "- роддом №" + maternities.get(i).getNumber()+" /n";
                }*/

            //  telegramBot.execute(new SendMessage(chatId, message));

        } else if (menuNumber==0) {
            switch (update.message().text()) {
                // выбираем файлы свойств в завивисмости от имени роддома
                //Роддом 40
                case MENU0_BUTTON1:
                    maternityName = MENU0_BUTTON1;
                    telegramBot.execute(new SendMessage(chatId, "Узнать информацию о роддоме: "+ maternityName));
                    properties = PropertiesLoaderUtils.loadProperties(new EncodedResource
                            (new ClassPathResource("info.40.properties"), StandardCharsets.UTF_8));
                    menuNumber = 1;
                    showMenu(chatId);
                    break;
                // роддом МОНИИАГ
                case MENU0_BUTTON2:
                    maternityName = MENU0_BUTTON2;
                    telegramBot.execute(new SendMessage(chatId, "Узнать информацию о роддоме: " + maternityName));
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
                    telegramBot.execute(new SendMessage(chatId, "(Узнать информацию о роддоме), Открыть меню 2"));
                    menuNumber = 2;
                    showMenu(chatId);
                    break;
                //"Что нужно взять с собой в роддом";
                case MENU1_BUTTON2:
                    telegramBot.execute(new SendMessage(chatId, "(Что нужно взять с собой в роддом), Открыть меню 3"));
                    menuNumber = 3;
                    showMenu(chatId);
                    break;
                //"Послеродовое сопровождение"
                case MENU1_BUTTON3:
                    telegramBot.execute(new SendMessage(chatId, "(Послеродовое сопровождение), Открыть меню 3"));
                    menuNumber = 3;
                    showMenu(chatId);
                    break;
                //"Связаться со специалистом"
                case MENU1_BUTTON4:
                    telegramBot.execute(new SendMessage(chatId, "Связать со специалистом"));
                    ;
                    break;
                //"Вернуться в предыдущее меню";
                case MENU1_BUTTON5:
                    telegramBot.execute(new SendMessage(chatId, "(Вернуться в предыдущее меню), Открыть меню 0"));
                    menuNumber = 0;
                    showMenu(chatId);
                    break;
                default:
                    telegramBot.execute(new SendMessage(chatId, "Связать со специалистом"));
            }
        } else if (menuNumber == 2) {

            switch (update.message().text()) {
                //Рассказать о роддоме подробнее";
                case MENU2_BUTTON1:
                    telegramBot.execute(new SendMessage(chatId, "Рассказать о роддоме " +maternityName+" подробнее"));
                    break;
                //""График работы роддома"";
                case MENU2_BUTTON2:
                    telegramBot.execute(new SendMessage(chatId, "График работы роддома " +maternityName));
                    break;
                //"Адрес, схема проезда "
                case MENU2_BUTTON3:
                    telegramBot.execute(new SendMessage(chatId, "Адрес, схема проезда "));
                    break;
                //"Что взять с собой в роддом";
                case MENU2_BUTTON5:
                    telegramBot.execute(new SendMessage(chatId, "(Что взять с собой в роддом), открыть меню 3"));
                    menuNumber = 3;
                    showMenu(chatId);
                    break;
                //"Связаться со специалистом"
                case MENU2_BUTTON6:
                    telegramBot.execute(new SendMessage(chatId, "Связаться со специалистом"));
                    break;
                //"Вернуться в предыдущее меню";
                case MENU2_BUTTON7:
                    telegramBot.execute(new SendMessage(chatId, "(Вернуться в предыдущее меню), Открыть меню 1"));
                    menuNumber = 1;
                    showMenu(chatId);
                    break;
                default:
                    telegramBot.execute(new SendMessage(chatId, "Связать со специалистом"));
            }

        } else if (menuNumber == 3) {
            switch (update.message().text()) {
                //"Какие документы взять?";
                case MENU3_BUTTON1:
                    telegramBot.execute(new SendMessage(chatId, properties.getProperty("documents.info")));
                    break;
                //"Первые дни в роддоме";
                case MENU3_BUTTON2:
                    telegramBot.execute(new SendMessage(chatId, properties.getProperty("staying.maternity.info")));
                    break;
                //"Забираем малыша домой. Рекомендации"
                case MENU3_BUTTON3:
                    telegramBot.execute(new SendMessage(chatId, properties.getProperty("comming.home.info")));
                    break;
                //"Список рекомендаций по обустройству дома для малыша"
                case MENU3_BUTTON4:
                    telegramBot.execute(new SendMessage(chatId, properties.getProperty("advices.baby.home.info")));
                    break;
                //"Список рекомендаций по обустройству дома для мамы"
                case MENU3_BUTTON5:
                    telegramBot.execute(new SendMessage(chatId, properties.getProperty("advices.mother.home.info")));
                    break;
                //"Советы неонатолога по уходу за ребенком в первые недели жизни"
                case MENU3_BUTTON6:
                    telegramBot.execute(new SendMessage(chatId, properties.getProperty("neonatologist.advices.info")));
                    break;
                //"Контактные данные рекомендованных педиатров"
                case MENU3_BUTTON7:
                    telegramBot.execute(new SendMessage(chatId, properties.getProperty("pediatrician.advices.info")));
                    break;
                //"Записаться на послеродовое сопровождение"
                case MENU3_BUTTON8:
                    telegramBot.execute(new SendMessage(chatId, "Записаться на послеродовое сопровождение"));
                    break;
                //"Связаться со специалистом"
                case MENU3_BUTTON9:
                    telegramBot.execute(new SendMessage(chatId, "Связаться со специалистом"));
                    break;
                //"Вернуться в предыдущее меню";
                case MENU3_BUTTON10:
                    telegramBot.execute(new SendMessage(chatId, "(Вернуться в предыдущее меню), Открыть меню 2"));
                    menuNumber = 2;
                    showMenu(chatId);
                    break;
                default:
                    telegramBot.execute(new SendMessage(chatId, "Связать со специалистом"));
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

}