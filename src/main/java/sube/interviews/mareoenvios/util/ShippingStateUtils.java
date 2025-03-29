package sube.interviews.mareoenvios.util;

import java.util.List;
import java.util.Map;

public class ShippingStateUtils {
    public static final Map<String, List<String>> ALLOWED_TRANSITIONS = Map.of(
            "Inicial", List.of("Entregado al correo", "Cancelado"),
            "Entregado al correo", List.of("En camino", "Cancelado"),
            "En camino", List.of("Entregado"),
            "Entregado", List.of(),  // Estado final
            "Cancelado", List.of()   // Estado final
    );

    public static boolean isValidTransition(String currentState, String newState) {
        return ALLOWED_TRANSITIONS.containsKey(currentState) &&
                ALLOWED_TRANSITIONS.get(currentState).contains(newState);
    }
}
