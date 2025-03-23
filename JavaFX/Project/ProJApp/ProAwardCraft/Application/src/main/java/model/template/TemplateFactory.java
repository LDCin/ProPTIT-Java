package model.template;

import model.element.TextComponent;

import java.util.ArrayList;
import java.util.List;

public class TemplateFactory {

    public static List<Template> getTemplates() {
        List<Template> templates = new ArrayList<>();

        Template giayKhen = new Template("Giấy khen", "#FF0000", "#FFF9E6", "Application/src/main/templates/giay_khen_preview.png");
        giayKhen.addTextComponent("title", new TextComponent("title", "GIẤY KHEN", "Giữa", 250, 40));
        giayKhen.addTextComponent("transition", new TextComponent("transition", "Được trao cho", "Giữa", 250, 70));
        giayKhen.addTextComponent("recipient", new TextComponent("recipient", "", "Giữa", 250, 110));
        giayKhen.addTextComponent("description", new TextComponent("description", "là thành viên xuất sắc của CLB Lập trình PTIT.", "Giữa", 250, 150));
        giayKhen.addTextComponent("award", new TextComponent("award", "Giải thưởng: ", "Giữa", 250, 190));
        giayKhen.addTextComponent("rightRole", new TextComponent("rightRole", "CHỦ NHIỆM CLB", "Phải", 400, 240));
        giayKhen.addTextComponent("owner", new TextComponent("owner", "", "Phải", 450, 270));
        templates.add(giayKhen);

        Template giayChungNhan = new Template("Giấy chứng nhận", "#005BB5", "#E6F0FA", "Application/src/main/templates/giay_chung_nhan_preview.png");
        giayChungNhan.addTextComponent("title", new TextComponent("title", "GIẤY CHỨNG NHẬN", "Giữa", 250, 50));
        giayChungNhan.addTextComponent("transition", new TextComponent("transition", "Được trao cho", "Giữa", 250, 80));
        giayChungNhan.addTextComponent("recipient", new TextComponent("recipient", "", "Giữa", 250, 120));
        giayChungNhan.addTextComponent("description", new TextComponent("description", "đã hoàn thành xuất sắc nhiệm vụ.", "Giữa", 250, 160));
        giayChungNhan.addTextComponent("award", new TextComponent("award", "Giải thưởng: ", "Giữa", 250, 200));
        giayChungNhan.addTextComponent("rightRole", new TextComponent("rightRole", "CHỦ NHIỆM CLB", "Phải", 400, 250));
        giayChungNhan.addTextComponent("owner", new TextComponent("owner", "", "Phải", 450, 280));
        templates.add(giayChungNhan);

        Template bangKhen = new Template("Bằng khen", "#FFD700", "#FFFFFF", "Application/src/main/templates/bang_khen_preview.png");
        bangKhen.addTextComponent("title", new TextComponent("title", "BẰNG KHEN", "Giữa", 250, 30));
        bangKhen.addTextComponent("transition", new TextComponent("transition", "Trao tặng", "Giữa", 250, 60));
        bangKhen.addTextComponent("recipient", new TextComponent("recipient", "", "Giữa", 250, 100));
        bangKhen.addTextComponent("description", new TextComponent("description", "vì có thành tích xuất sắc.", "Giữa", 250, 140));
        bangKhen.addTextComponent("award", new TextComponent("award", "Giải thưởng: ", "Giữa", 250, 180));
        bangKhen.addTextComponent("rightRole", new TextComponent("rightRole", "CHỦ NHIỆM CLB", "Phải", 400, 230));
        bangKhen.addTextComponent("owner", new TextComponent("owner", "", "Phải", 450, 260));
        templates.add(bangKhen);

        Template chungNhanThanhTich = new Template("Chứng nhận thành tích", "#000000", "#E6FFE6", "Application/src/main/templates/chung_nhan_thanh_tich_preview.png");
        chungNhanThanhTich.addTextComponent("title", new TextComponent("title", "CHỨNG NHẬN THÀNH TÍCH", "Giữa", 250, 50));
        chungNhanThanhTich.addTextComponent("transition", new TextComponent("transition", "Công nhận", "Giữa", 250, 80));
        chungNhanThanhTich.addTextComponent("recipient", new TextComponent("recipient", "", "Giữa", 250, 120));
        chungNhanThanhTich.addTextComponent("description", new TextComponent("description", "đã đạt thành tích nổi bật.", "Giữa", 250, 160));
        chungNhanThanhTich.addTextComponent("award", new TextComponent("award", "Giải thưởng: ", "Giữa", 250, 200));
        chungNhanThanhTich.addTextComponent("rightRole", new TextComponent("rightRole", "CHỦ NHIỆM CLB", "Phải", 400, 250));
        chungNhanThanhTich.addTextComponent("owner", new TextComponent("owner", "", "Phải", 450, 280));
        templates.add(chungNhanThanhTich);

        Template giayVinhDanh = new Template("Giấy vinh danh", "#800080", "#F5E6FF", "Application/src/main/templates/giay_vinh_danh_preview.png");
        giayVinhDanh.addTextComponent("title", new TextComponent("title", "GIẤY VINH DANH", "Giữa", 250, 40));
        giayVinhDanh.addTextComponent("transition", new TextComponent("transition", "Tôn vinh", "Giữa", 250, 70));
        giayVinhDanh.addTextComponent("recipient", new TextComponent("recipient", "", "Giữa", 250, 110));
        giayVinhDanh.addTextComponent("description", new TextComponent("description", "vì những đóng góp xuất sắc.", "Giữa", 250, 150));
        giayVinhDanh.addTextComponent("award", new TextComponent("award", "Giải thưởng: ", "Giữa", 250, 190));
        giayVinhDanh.addTextComponent("rightRole", new TextComponent("rightRole", "CHỦ NHIỆM CLB", "Phải", 400, 240));
        giayVinhDanh.addTextComponent("owner", new TextComponent("owner", "", "Phải", 450, 270));
        templates.add(giayVinhDanh);

        return templates;
    }
}