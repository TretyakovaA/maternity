package pro.sky.maternity.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.maternity.dto.MaternityHospitalDto;
import pro.sky.maternity.mapper.MaternityHospitalDtoMapper;
import pro.sky.maternity.repository.MaternityHospitalRepository;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class TelegramUpdateHandler {

    private final Logger logger = LoggerFactory.getLogger(TelegramUpdateHandler.class);

    private final TelegramBot telegramBot;

//меню1
    public final String MENU1_BUTTON1 = "Узнать информацию о роддоме";
    public final String MENU1_BUTTON2 = "Что нужно взять с собой в роддом";
    public final String MENU1_BUTTON3 = "Записаться на послеродовое сопровождение";
    public final String MENU1_BUTTON4 = "Связаться со специалистом";

    private final MaternityHospitalRepository maternityHospitalRepository;
    private final MaternityHospitalDtoMapper maternityHospitalDtoMapper;

    public TelegramUpdateHandler(TelegramBot telegramBot, MaternityHospitalRepository maternityHospitalRepository, MaternityHospitalDtoMapper maternityHospitalDtoMapper) {
        this.telegramBot = telegramBot;
        this.maternityHospitalRepository = maternityHospitalRepository;
        this.maternityHospitalDtoMapper = maternityHospitalDtoMapper;
    }

    /**
     * Фасад, основная логика построения меню бота и обработки статусов
     * По окончанию метода, отправляем сообщение или редактируем, сохраняем ответ полученный от телеги
     *
     * @param update Update
     */
    public void processUpdate(Update update) {
            logger.info("Processing update: {}", update);
            // Process your updates here
            long chatId = update.message().chat().id();
            if (update.message().text().equals("/start")) {
                String message = "Добро пожаловать! Какой роддом Вас интересует? /n";
               /* List<MaternityHospitalDto> maternities = maternityHospitalDtoMapper.toDtos(maternityHospitalRepository.findAll());
                for (int i = 0; i < maternities.size(); i++) {
                    message = message + maternities.get(i).getNumber() +  "- роддом №" + maternities.get(i).getNumber()+" /n";
                }*/

              //  telegramBot.execute(new SendMessage(chatId, message));
                telegramBot.execute(new SendMessage(chatId,message).replyMarkup(new ReplyKeyboardMarkup(new String[]{MENU1_BUTTON1}, new String[]{MENU1_BUTTON2}, new String[]{MENU1_BUTTON3}, new String[]{MENU1_BUTTON4})));
            } else {
                String text = update.message().text();

                switch (text) {
                    case MENU1_BUTTON1:
                        telegramBot.execute(new SendMessage(chatId, "Открыть меню 2"));
                        break;
                    case MENU1_BUTTON2:
                        telegramBot.execute(new SendMessage(chatId, "Открыть меню 3"));
                        break;
                    case MENU1_BUTTON3:
                        telegramBot.execute(new SendMessage(chatId, "Открыть форму записи"));;
                        break;
                    case MENU1_BUTTON4:
                        telegramBot.execute(new SendMessage(chatId, "Связать со специалистом"));;
                        break;
                    default:
                        telegramBot.execute(new SendMessage(chatId, "Связать со специалистом"));
                }


            };
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