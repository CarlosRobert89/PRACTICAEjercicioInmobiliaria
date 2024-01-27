package carlos.robert.ejercicioinmobiliaria;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import carlos.robert.ejercicioinmobiliaria.databinding.ActivityMainBinding;
import carlos.robert.ejercicioinmobiliaria.modelos.Inmueble;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private ActivityResultLauncher<Intent> launcherAddInmueble;
    private ActivityResultLauncher<Intent> launcherEditInmueble;
    private ArrayList<Inmueble> listaInmuebles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        listaInmuebles = new ArrayList<>();
        inicializarLaunchers();


        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launcherAddInmueble.launch(new Intent(MainActivity.this, AddInmuebleActivity.class));
            }
        });
    }

    private void inicializarLaunchers() {
        launcherAddInmueble = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        //resultado de volver de la actividad AddInmueble
                        if (result.getData() != null && result.getData().getExtras() != null) {
                            Inmueble inmueble = (Inmueble) result.getData().getExtras().getSerializable("INMUEBLE");
                            listaInmuebles.add(inmueble);
                            mostrarInmuebles();
                        } else {
                            Toast.makeText(MainActivity.this, "ACCIÓN CANCELADA", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
        launcherEditInmueble = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult o) {
                        //aquí volveré después de la actividad editar inmueble
                        if (o.getResultCode() == RESULT_OK) {
                            if (o.getData() != null && o.getData().getExtras() != null) {
                                Inmueble inmueble = (Inmueble)o.getData().getExtras().getSerializable("INMUEBLE");
                                int posicion = o.getData().getExtras().getInt("POSICION");

                                if (inmueble == null) {
                                    //eliminar el inmueble de esa posición
                                    listaInmuebles.remove(posicion);
                                } else {
                                    //editar el inmueble de esa posición
                                    listaInmuebles.set(posicion, inmueble);
                                }
                                mostrarInmuebles();
                            } else {
                                Toast.makeText(MainActivity.this, "ACCIÓN CANCELADA", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(MainActivity.this, "ESCAPE", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }

    private void mostrarInmuebles() {
        //borrar el contenido del LinearLayout
        binding.contenedor.contentMain.removeAllViews();
        for (int i = 0; i < listaInmuebles.size(); i++) {
            Inmueble inmueble = listaInmuebles.get(i);
            //crear la fila de la vista inmuebleModelView. Siempre que quiera utilizar alguna vista
            //que no sea la que tengo asociada en la actividad(en este caso Main), tenemos que usar
            //el LayoutInflater decirle donde lo voy a usar y qué vista(layout) voy a usar.
            View inmuebleView = LayoutInflater.from(MainActivity.this).inflate(R.layout.inmueble_model_view, null);
            //al no estar esta vista asociada a ninguna actividad cogemos los parámetros con R.id.
            TextView lbDireccion = inmuebleView.findViewById(R.id.lbDireccionInmuebleModelView);
            TextView lbNumero = inmuebleView.findViewById(R.id.lbNumeroInmuebleModelView);
            TextView lbCiudad = inmuebleView.findViewById(R.id.lbCiudadInmuebleModelView);
            RatingBar rbValoracion = inmuebleView.findViewById(R.id.rbValoracionInmuebleModelView);

            lbDireccion.setText(inmueble.getDireccion());
            lbNumero.setText(String.valueOf(inmueble.getNumero()));
            lbCiudad.setText(inmueble.getCiudad());
            rbValoracion.setRating(inmueble.getValoracion());

            int posicion = i;
            inmuebleView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, EditInmuebleActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("INMUEBLE", inmueble);
                    bundle.putInt("POSICION", posicion);
                    intent.putExtras(bundle);

                    launcherEditInmueble.launch(intent);
                }
            });

            binding.contenedor.contentMain.addView(inmuebleView);
        }
    }
}