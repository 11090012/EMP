 package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat.RegisterReceiverFlags

 class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Функция, чтобы удалить из строки все елементы, кроме цифр
        fun removeNonDigits(str: String): String {
            return str.filter { it.isDigit() }
        }
         // Функция, чтобы добавить "+" в начало строки
        fun addPlusSign(str: String): String {
            return if (str.isNotEmpty()) "+$str" else ""
        }

    //Находим на XML странице все элементы с которыми будем работать
        val name: EditText = findViewById(R.id.Name)
        val lastName: EditText = findViewById(R.id.LastName)
        val phone: EditText = findViewById(R.id.Phone)
        val register: Button = findViewById(R.id.Register)

        // Добавляем обработчик нажатия на кнопку "Register"
        register.setOnClickListener {
            // При нажатии записываем в переменные все, что было запиано в полях
            // Приводим все к строке и убираем пробелы с помощью trim()
            val userName = name.text.toString().trim()
            val userLastName = lastName.text.toString().trim()
            val userPhone = phone.text.toString().trim()

            //Делаем проверки, если хотябы одно поле пустое, не пускаем
            if(userName == "" || userLastName == "" || userPhone ==""){
                Toast.makeText(this, "Not all fields are filled in", Toast.LENGTH_LONG).show()
            } else{
                //Если имя или фамилия содержат цифры, не пускаем
                if (userName.any{it.isDigit()} || userLastName.any{it.isDigit()}){
                    Toast.makeText(this, "First and last names must not contain numbers", Toast.LENGTH_LONG).show()
                } else{
                    //Если имя или фамилия по длине не равны 2-25 символов, не пускаем
                    if(userName.length<2 || userName.length > 25 || userLastName.length<2 || userLastName.length>25){
                        Toast.makeText(this, "First and last name must contain 2-25 characters", Toast.LENGTH_LONG).show()
                    }else{
                        //Если номен по длине не равен 7-25, не пускаем
                        if(userPhone.length<7 || userPhone.length>25){
                            Toast.makeText(this, "The phone number must contain 7-25 characters", Toast.LENGTH_LONG).show()
                        }else{
                            //Если все условия соблюдены, сообщаем об успешной регистрации
                            Toast.makeText(this, "User $userName is registered", Toast.LENGTH_LONG).show()
                            //Очищаем поля
                            name.text.clear()
                            lastName.text.clear()
                            phone.text.clear()
                            //Удаляем из номера все, кроме цифр
                            val digitsOnly = removeNonDigits(userPhone)
                            //Добавляем к номеру "+" в начало
                            val withPlusSign = addPlusSign(digitsOnly)
                            //Создаем переменную с переходом на новую активити
                            val intent = Intent(this, Taxi::class.java)
                            // с попощью этого непонятного intent передаем имя, фамилию и номер в следующую активити
                            // с помощью capitalize() делаем имя и фамилию с большой буквы
                            intent.putExtra("userName", userName.capitalize())
                            intent.putExtra("userLastName", userLastName.capitalize())
                            intent.putExtra("userPhone", withPlusSign)
                            //переходим на новую активити
                            startActivity(intent)
                        }

                    }

                }

            }


        }




    }
}