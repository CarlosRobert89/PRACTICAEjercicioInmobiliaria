package carlos.robert.ejercicioinmobiliaria;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import carlos.robert.ejercicioinmobiliaria.databinding.ActivityEditInmuebleBinding;
import carlos.robert.ejercicioinmobiliaria.modelos.Inmueble;

public class EditInmuebleActivity extends AppCompatActivity {
    private ActivityEditInmuebleBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityEditInmuebleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Inmueble inmueble = (Inmueble) bundle.getSerializable("INMUEBLE");
        int posicion = bundle.getInt("POSICION");

        rellenarVista(inmueble);

        binding.btnEliminarEditarInmueble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent();
                Bundle bundle2 = new Bundle();
                bundle2.putInt("POSICION", posicion);
                intent2.putExtras(bundle2);

                setResult(RESULT_OK, intent2);
                finish();
            }
        });

        binding.btnEditarEditarInmueble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Inmueble inmuebleEditado = crearInmueble();

                if(inmuebleEditado==null){
                    Toast.makeText(EditInmuebleActivity.this, "FALTAN DATOS", Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent3 = new Intent();
                    Bundle bundle3 = new Bundle();
                    bundle3.putSerializable("INMUEBLE", inmuebleEditado);
                    bundle3.putInt("POSICION", posicion);
                    intent3.putExtras(bundle3);

                    setResult(RESULT_OK, intent3);
                    finish();
                }
            }
        });
    }

    private void rellenarVista(Inmueble inmueble) {
        binding.txtDireccionEditInmueble.setText(inmueble.getDireccion());
        binding.txtNumeroEditInmueble.setText(String.valueOf(inmueble.getNumero()));
        binding.txtCiudadEditInmueble.setText(inmueble.getCiudad());
        binding.txtProvinciaEditInmueble.setText(inmueble.getProvincia());
        binding.txtCPEditInmueble.setText(inmueble.getCp());
        binding.rbValoracionEditInmueble.setRating(inmueble.getValoracion());
    }

    private Inmueble crearInmueble() {
        if (binding.txtDireccionEditInmueble.getText().toString().isEmpty()
                || binding.txtNumeroEditInmueble.getText().toString().isEmpty()
                || binding.txtCiudadEditInmueble.getText().toString().isEmpty()
                || binding.txtProvinciaEditInmueble.getText().toString().isEmpty()
                || binding.txtCPEditInmueble.getText().toString().isEmpty()
                ) {
            return null;
        } else {
            return new Inmueble(
                    binding.txtDireccionEditInmueble.getText().toString(),
                    Integer.parseInt(binding.txtNumeroEditInmueble.getText().toString()),
                    binding.txtCiudadEditInmueble.getText().toString(),
                    binding.txtProvinciaEditInmueble.getText().toString(),
                    binding.txtCPEditInmueble.getText().toString(),
                    binding.rbValoracionEditInmueble.getRating()
            );
        }
    }
}