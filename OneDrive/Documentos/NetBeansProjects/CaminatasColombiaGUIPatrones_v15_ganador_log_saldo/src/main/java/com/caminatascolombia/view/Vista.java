
package com.caminatascolombia.view;

import java.util.List;
import com.caminatascolombia.model.Caminata;

public interface Vista {
    void mostrarMensaje(String mensaje);
    void mostrarCaminatas(List<Caminata> caminatas);
}
