/*using UnityEngine;
using UnityEngine.UI;

public class CHEAT : MonoBehaviour
{
    public int m_PlayerNumber = 1;
    private string[] cheatCode;
    private int index;

    void Start()
    {
        cheatCode = new string[] { "q", "e"};
        index = 0;
    }

    void Update()
    {
        // Check if any key is pressed
        if (Input.anyKeyDown)
        {
            // Check if the next key in the code is pressed
            if (Input.GetKeyDown(cheatCode[index]))
            {
                // Add 1 to index to check the next key in the code
                index++;
            }
            // Wrong key entered, we reset code typing
            else
            {
                index = 0;
            }
        }

        // If index reaches the length of the cheatCode string, 
        // the entire code was correctly entered
        if (index == cheatCode.Length)
        {
            // Cheat code successfully inputted!
            // Unlock crazy cheat code stuff
            TankHealth targetHealth;
            targetHealth.set_pollaplasiastis(2);
        }
    }
}*/