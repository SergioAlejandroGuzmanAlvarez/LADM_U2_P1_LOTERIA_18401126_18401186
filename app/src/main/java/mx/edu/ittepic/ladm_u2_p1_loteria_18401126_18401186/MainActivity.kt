package mx.edu.ittepic.ladm_u2_p1_loteria_18401126_18401186

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import kotlinx.coroutines.*
import mx.edu.ittepic.ladm_u2_p1_loteria_18401126_18401186.databinding.ActivityMainBinding
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    lateinit var  binding : ActivityMainBinding
    var pausar = false
    var continuar = false
    var empezar = false
    var VectorCartas = arrayOf (R.drawable.carta1,R.drawable.carta2,R.drawable.carta3,R.drawable.carta4,R.drawable.carta5,
    R.drawable.carta6,R.drawable.carta7,R.drawable.carta8,R.drawable.carta9,R.drawable.carta10,R.drawable.carta11,
    R.drawable.carta12,R.drawable.carta13,R.drawable.carta14,R.drawable.carta15,R.drawable.carta16,R.drawable.carta17,
    R.drawable.carta18,R.drawable.carta19,R.drawable.carta20,R.drawable.carta21,R.drawable.carta22,R.drawable.carta23,
    R.drawable.carta24,R.drawable.carta25,R.drawable.carta26,R.drawable.carta27,R.drawable.carta28,R.drawable.carta29,
    R.drawable.carta30,R.drawable.carta31,R.drawable.carta32,R.drawable.carta33,R.drawable.carta34,R.drawable.carta35,
    R.drawable.carta36,R.drawable.carta37,R.drawable.carta38,R.drawable.carta39,R.drawable.carta40,R.drawable.carta41,
    R.drawable.carta42,R.drawable.carta43,R.drawable.carta44,R.drawable.carta45,R.drawable.carta46,R.drawable.carta47,
    R.drawable.carta48,R.drawable.carta49,R.drawable.carta50,R.drawable.carta51,R.drawable.carta52,R.drawable.carta53,R.drawable.carta54)
    var VectorAudios = arrayOf (R.raw.carta1,R.raw.carta2,R.raw.carta3,R.raw.carta4,R.raw.carta5,
        R.raw.carta6,R.raw.carta7,R.raw.carta8,R.raw.carta9,R.raw.carta10,R.raw.carta11,
        R.raw.carta12,R.raw.carta13,R.raw.carta14,R.raw.carta15,R.raw.carta16,R.raw.carta17,
        R.raw.carta18,R.raw.carta19,R.raw.carta20,R.raw.carta21,R.raw.carta22,R.raw.carta23,
        R.raw.carta24,R.raw.carta25,R.raw.carta26,R.raw.carta27,R.raw.carta28,R.raw.carta29,
        R.raw.carta30,R.raw.carta31,R.raw.carta32,R.raw.carta33,R.raw.carta34,R.raw.carta35,
        R.raw.carta36,R.raw.carta37,R.raw.carta38,R.raw.carta39,R.raw.carta40,R.raw.carta41,
        R.raw.carta42,R.raw.carta43,R.raw.carta44,R.raw.carta45,R.raw.carta46,R.raw.carta47,
        R.raw.carta48,R.raw.carta49,R.raw.carta50,R.raw.carta51,R.raw.carta52,R.raw.carta53,R.raw.carta54)
    var audio : MediaPlayer?= null
    var VectorBarajas = ArrayList<Int>()
    val scope = CoroutineScope(Job() + Dispatchers.Main)
    var puntero = this
    var contador = 1
    var mp = MediaPlayer()
    val coroutineCartas = scope.launch(EmptyCoroutineContext, CoroutineStart.LAZY){
        while(true){
            runOnUiThread{
                if(empezar==true){
                    audio = MediaPlayer.create(puntero,VectorAudios[VectorBarajas[contador]])
                    binding.imgCartas.setImageResource(VectorCartas[VectorBarajas[contador]])
                    audio?.start()
                }
                if(pausar==true){
                    binding.txtLoteria1.setText("TENEMOS NUEVO GANADOR")
                    empezar=false
                }else if(!pausar && continuar){
                    binding.txtLoteria1.text = "CARTAS FALTANTES"
                    audio = MediaPlayer.create(puntero,VectorAudios[VectorBarajas[contador]])
                    binding.imgCartas.setImageResource(VectorCartas[VectorBarajas[contador]])
                    audio?.start()

                }
                binding.txtLoteria2.setText("CARTA #${contador++}")
            }
            if(contador == VectorCartas.size) {
                empezar=false
                AlertDialog.Builder(binding.root.context)
                    .setTitle("SE TERMINO LA LOTERIA")
                    .setMessage("¿Quieres empezar de nuevo?")
                    .setPositiveButton("OK"){d,i->
                        contador=1
                        nuevaBaraja()
                        empezar=true
                        binding.txtLoteria1.setText("La Loteria Mexicana")
                    }
                    .show()
            }
            delay(3000L)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnIniciar.setEnabled(true)
        binding.btnIniciar.setOnClickListener{
            coroutineCartas.start()
            empezar = true
            nuevaBaraja()
            binding.btnSuspender.setEnabled(true)
            binding.btnVerificar.setEnabled(true)
            binding.btnIniciar.setEnabled(false)
        }
        binding.btnSuspender.setOnClickListener{
            pausar = true
        }
        binding.btnVerificar.setOnClickListener{
            binding.btnSuspender.setEnabled(false)
            pausar=false
            continuar=true
        }
    }
    fun nuevaBaraja(){
        VectorBarajas = ArrayList<Int>()
        for(i in VectorCartas){
            VectorBarajas.add(posicionAzar(VectorBarajas))
        }
    }
    fun posicionAzar(numPos:ArrayList<Int>):Int{
        var hasta = Random.nextInt()
        hasta = (Math.random()*54).toInt()
        while(numPos.contains(hasta)){
            return posicionAzar(numPos)
        }
        return hasta
    }
}