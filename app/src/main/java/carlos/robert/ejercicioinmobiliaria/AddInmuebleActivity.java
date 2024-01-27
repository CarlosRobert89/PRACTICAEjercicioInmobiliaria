package carlos.robert.ejercicioinmobiliaria;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import carlos.robert.ejercicioinmobiliaria.databinding.ActivityAddInmuebleBinding;
import carlos.robert.ejercicioinmobiliaria.modelos.Inmueble;

public class AddInmuebleActivity extends AppCompatActivity {
    private ActivityAddInmuebleBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAddInmuebleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnCancelarAddInmueble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });

        binding.btnInsertarAddInmueble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Inmueble inmueble = crearInmueble();
                if (inmueble != null) {
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("INMUEBLE", inmueble);
                    intent.putExtras(bundle);

                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    Toast.makeText(AddInmuebleActivity.this, "FALTAN DATOS", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private Inmueble crearInmueble() {
        if (binding.txtDireccionAddInmueble.getText().toString().isEmpty()
                || binding.txtNumeroAddInmueble.getText().toString().isEmpty()
                || binding.txtCPAddInmueble.getText().toString().isEmpty()
                || binding.txtCiudadAddInmueble.getText().toString().isEmpty()
                || binding.txtProvinciaAddInmueble.getText().toString().isEmpty()) {
            return null;
        } else {
            return new Inmueble(
                    binding.txtDireccionAddInmueble.getText().toString(),
                    Integer.parseInt(binding.txtNumeroAddInmueble.getText().toString()),
                    binding.txtCPAddInmueble.getText().toString(),
                    binding.txtCiudadAddInmueble.getText().toString(),
                    binding.txtProvinciaAddInmueble.getText().toString(),
                    binding.rbValoracionAddInmueble.getRating()
            );
        }
    }
}