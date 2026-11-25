package org.crudfeed.cli.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.crudfeed.cli.network.ResponseClient;
import org.crudfeed.core.dto.mod.ModActionRoleResultResponseDto;
import org.crudfeed.core.dto.mod.ModInfoRequestDto;

import static java.lang.IO.println;
import static java.lang.IO.readln;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ModHandler {

    @Getter
    private static final ModHandler INSTANCE = new ModHandler();

    private final ResponseClient api = ResponseClient.getINSTANCE();
    private final ObjectMapper objectMapper = new ObjectMapper();


    public void modAction(
            long modId,
            long targetId,
            String action,
            String targetType,
            String actionPhrase,
            String cancelText
    ) {
        if (modId <= 0 || targetId <= 0) {
            println("Invalid mod or " + targetType + " ID!");
            return;
        }

        String confirm = readln("Are you sure you want to " + actionPhrase + " ? (y/n): ");

        if (!"y".equalsIgnoreCase(confirm)) {
            println(cancelText);
            return;
        }

        ModInfoRequestDto dto = new ModInfoRequestDto(modId, targetId);

        String response = api.sendPost(action, dto);

        try {
            ModActionRoleResultResponseDto result = objectMapper.readValue(response, ModActionRoleResultResponseDto.class);

            if (result == null) {
                println("Invalid mod response!");
                return;
            }

            println(result.text());

        } catch (Exception e) {
            println(response);
        }
    }

    public String modShow(long modId, long targetId) {
        if (modId <= 0 || targetId <= 0) {
            return "Invalid mod or user ID!";
        }

        String confirm = readln("Are you sure you want to see account info? (y/n): ");

        if (!"y".equalsIgnoreCase(confirm)) {
            return "Show account cancelled.";
        }

        ModInfoRequestDto dto = new ModInfoRequestDto(modId, targetId);

        return api.sendPost("modShow", dto);
    }
}