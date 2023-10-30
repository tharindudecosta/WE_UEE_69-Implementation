import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.ayusri.R

class BuyMedicine : AppCompatActivity() {
    private lateinit var tvDisTopic: TextView
    private lateinit var tvDisAdd: TextView
    private lateinit var medprice: TextView
    private lateinit var submit: Button
    private lateinit var qtys: EditText
    private lateinit var cusnames: EditText
    private lateinit var cAddress: EditText
    private lateinit var prices: TextView
    private var totalPrice: Double = 0.0
    private lateinit var confirm: Button

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.xactivity_buyy_medicine)

        sharedPreferences = getSharedPreferences("BuyMedicinePrefs", MODE_PRIVATE)

        initView()
        setValueToViews()
        restoreSavedData()

        submit.setOnClickListener {
            calculateTotalPrice()
        }

        confirm.setOnClickListener {
            val quantity = qtys.text.toString().toIntOrNull()
            val customerName = cusnames.text.toString()
            val customerAddress = cAddress.text.toString()

            if (quantity != null && customerName.isNotBlank() && customerAddress.isNotBlank()) {
                // TODO: Perform the necessary operations with the calculated total price and customer details
                Toast.makeText(this, "Submitted successfully!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Invalid input", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setValueToViews() {
        tvDisTopic.text = intent.getStringExtra("mediTopic")
        tvDisAdd.text = intent.getStringExtra("mediAdd")
        medprice.text = intent.getStringExtra("mediPrice")
    }

    private fun initView() {
        tvDisTopic = findViewById(R.id.tvmedicine)
        tvDisAdd = findViewById(R.id.tvDiscription)
        medprice = findViewById(R.id.tvprice)
        submit = findViewById(R.id.subbutton)
        qtys = findViewById(R.id.qty)
        cusnames = findViewById(R.id.cusname)
        cAddress = findViewById(R.id.cusAddress)
        prices = findViewById(R.id.price)
        confirm = findViewById(R.id.confirm)
    }

    private fun calculateTotalPrice() {
        val quantity = qtys.text.toString().toIntOrNull()
        val unitPrice = intent.getStringExtra("mediPrice")?.toDoubleOrNull()

        if (quantity != null && unitPrice != null) {
            totalPrice = quantity * unitPrice
            prices.text = totalPrice.toString()

            saveData(quantity)
        } else {
            prices.text = "Invalid input"
        }
    }

    private fun saveData(quantity: Int) {
        val editor = sharedPreferences.edit()
        editor.putInt("quantity", quantity)
        editor.apply()
    }

    private fun restoreSavedData() {
        val quantity = sharedPreferences.getInt("quantity", 0)
        qtys.setText(quantity.toString())
        calculateTotalPrice()
    }
}
