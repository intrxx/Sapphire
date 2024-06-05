class Player(val name:String, private val sign:String) 
{
    fun GetSign():String
    {
        return sign
    }
    
    fun GetName():String
    {
        return name
    }
}