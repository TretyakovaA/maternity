package pro.sky.maternity.mapper;

import org.springframework.stereotype.Component;
import pro.sky.maternity.dto.ReportsDto;
import pro.sky.maternity.model.Reports;
@Component
public class ReportsDtoMapper {
    public ReportsDto toDto (Reports reports){
        ReportsDto reportsDto = new ReportsDto();
        reportsDto.setId(reports.getId());
        reportsDto.setDate(reports.getDate());
        reportsDto.setPhoto(reports.getPhoto());
        reportsDto.setText(reports.getText());
        reportsDto.setChatId(reports.getChatId());
        reportsDto.setUser(reports.getUser());
        return reportsDto;
    }

    public Reports toEntity (ReportsDto reportsDto){
        Reports reports = new Reports();
        reports.setId(reportsDto.getId());
        reports.setDate(reportsDto.getDate());
        reports.setPhoto(reportsDto.getPhoto());
        reports.setText(reportsDto.getText());
        reports.setChatId(reportsDto.getChatId());
        reports.setUser(reportsDto.getUser());
        return reports;
    }
}
